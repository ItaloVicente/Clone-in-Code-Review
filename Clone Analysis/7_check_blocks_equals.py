import configparser
import csv
import os
import hashlib
import shutil
from collections import defaultdict

def analise_revisions(caminho_type_clones):
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)

        antecessor = None
        revisions = {}
        L = []
        for linha in leitor_csv:
            atual = linha[0] + "_" + linha[1]
            if antecessor is None:
                antecessor = atual

            if atual != antecessor:
                revisions[antecessor] = L
                L = []
                antecessor = atual
            L.append(linha[2].split(".")[0])

        # Handle the last group after the loop
        if antecessor is not None:
            revisions[antecessor] = L
    return revisions

def hash_file(file_path):
    """Gera um hash SHA-256 para o conteúdo do arquivo."""
    hash_sha256 = hashlib.sha256()
    with open(file_path, 'rb') as f:
        for chunk in iter(lambda: f.read(4096), b""):
            hash_sha256.update(chunk)
    return hash_sha256.hexdigest()

def verificar_blocos_com_hash(dict_revisions_blocks, project):
    hash_map = {}  # Mapeia hash para informações do bloco
    blocos_iguais = set()
    blocos_diferentes = set()

    for review_block, blocks in dict_revisions_blocks.items():
        review, rev_num = review_block.split('_')
        for block in blocks:
            block_parts = block.rsplit('_', 1)
            block_name = block_parts[0]
            block_num = block_parts[1]
            path_block = f"blocks/{project}/{review}_{rev_num}/{block_name}_{block_num}.java"

            if os.path.exists(path_block):
                block_hash = hash_file(path_block)
                block_info = (review, rev_num, block_name, block_num)

                if block_hash in hash_map:
                    # Verificar se já existe uma entrada com o mesmo hash
                    other_block_info = hash_map[block_hash]
                    if other_block_info[0] == review and other_block_info[1] != rev_num:
                        if (block_info, other_block_info) not in blocos_iguais and (other_block_info, block_info) not in blocos_iguais:
                            blocos_iguais.add((block_info, other_block_info))
                else:
                    # Adicionar nova entrada no hash_map
                    hash_map[block_hash] = block_info

                # Verificar blocos diferentes na mesma revisão
                for other_review_block, other_blocks in dict_revisions_blocks.items():
                    other_review, other_rev_num = other_review_block.split('_')
                    if review == other_review and rev_num != other_rev_num:
                        for other_block in other_blocks:
                            if other_block == block:
                                other_block_parts = other_block.rsplit('_', 1)
                                other_block_name = other_block_parts[0]
                                other_block_num = other_block_parts[1]
                                path_other_block = f"blocks/{project}/{other_review}_{other_rev_num}/{other_block_name}_{other_block_num}.java"

                                if os.path.exists(path_other_block):
                                    other_block_hash = hash_file(path_other_block)
                                    if block_hash != other_block_hash:
                                        if (review_block, block, other_review_block) not in blocos_diferentes and (other_review_block, block, review_block) not in blocos_diferentes:
                                            blocos_diferentes.add((review_block, block, other_review_block))

    return list(blocos_iguais), list(blocos_diferentes)

def transitive_closure(blocos_iguais):
    # Construir o grafo
    graph = defaultdict(set)
    for bloco1, bloco2 in blocos_iguais:
        graph[bloco1].add(bloco2)
        graph[bloco2].add(bloco1)

    # Encontrar componentes conexos
    def dfs(node, visited, component):
        stack = [node]
        while stack:
            n = stack.pop()
            if n not in visited:
                visited.add(n)
                component.add(n)
                for neighbor in graph[n]:
                    if neighbor not in visited:
                        stack.append(neighbor)

    visited = set()
    componentes_conexos = []
    for node in graph:
        if node not in visited:
            component = set()
            dfs(node, visited, component)
            componentes_conexos.append(component)

    # Encontrar a menor revisão em cada componente conexo
    blocos_iguais_final = set()
    for component in componentes_conexos:
        menor_revisao = min(component, key=lambda x: int(x[1]))
        for bloco in component:
            blocos_iguais_final.add((bloco, menor_revisao))

    return blocos_iguais_final

def modificar_csv(caminho_type_clones, linhas):
    with open(caminho_type_clones, 'w', newline='') as csvfile:
        escritor_csv = csv.writer(csvfile, delimiter='\t')
        escritor_csv.writerows(linhas)  # Escrever todas as linhas (incluindo o cabeçalho)

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_do_arquivo = 'metadata/' + PROJECT + '.csv'
caminho_type_clones = "type_clones/" + PROJECT + ".csv"
caminho_backup = "type_clones/" + PROJECT + "(original).csv"
if not os.path.exists(caminho_backup):
    with open(caminho_backup, "w") as f:
        f.write("")
shutil.copy(caminho_type_clones, caminho_backup)
caminho_review_analysis = "review analysis/" + PROJECT + ".csv"
revisions = analise_revisions(caminho_type_clones)
blocos_iguais, blocos_diferentes = verificar_blocos_com_hash(revisions, PROJECT)

# Aplicar fechamento transitivo para blocos iguais
blocos_iguais_final = transitive_closure(blocos_iguais)

blocks_equals = []
for bloco in blocos_iguais_final:
    revision1 = bloco[0][0] + "_" + bloco[0][1]
    revision2 = bloco[1][0] + "_" + bloco[1][1]
    bloco1 = bloco[0][2] + "_" + bloco[0][3]
    bloco2 = bloco[1][2] + "_" + bloco[1][3]
    tupla = (revision1, bloco1, revision2, bloco2)
    blocks_equals.append(tupla)

L = set()
for block in blocos_diferentes:
    string_block = block[0] + "_" + block[1] + "-" + block[2]
    string_block2 = block[2] + "_" + block[1] + "-" + block[0]
    with open(caminho_type_clones, newline='') as csvfile:
        leitor_csv = csv.reader(csvfile, delimiter='\t')
        header = next(leitor_csv)  # Ignorar o cabeçalho

        for linha in leitor_csv:
            linha = linha[0].split(",")
            review_number = linha[0]
            revision_number = linha[1]
            arquivo_positivo = linha[2]

            # Construir a string de revisão conforme seu padrão
            string_revision = f"{review_number}_{revision_number}_{arquivo_positivo.split('.')[0]}-{block[2]}"

            # Verificar se a string de revisão corresponde a string_block ou string_block2
            if string_block == string_revision or string_block2 == string_revision:
                L.add(string_revision)

linhas = []
with open(caminho_type_clones, newline='') as csvfile:
    leitor_csv = csv.reader(csvfile, delimiter='\t')
    header = next(leitor_csv)  # Capturar o cabeçalho
    linhas.append(header)  # Adicionar o cabeçalho à lista de linhas
    for linha in leitor_csv:
        linhas.append(linha)

for block in sorted(list(L)):
    review_atual = block.split("_")[0]
    revision_atual = block.split("_")[1]
    review_para_trocar = block.split("_")[3].split("-")[1]
    number_block = block.split("_")[3].split("-")[0]
    revision_para_trocar = block.split("_")[4]
    block_atual = block.split("_")[2] + '_' + number_block
    print(review_atual + " Trocando block da review:" + review_para_trocar + " revision: " + revision_atual + " rev para trocar: " + revision_para_trocar)

    for i in range(1, len(linhas)):  # Iterar a partir da posição 1, ignorando a posição 0
        linha = linhas[i][0].split(",")
        block_do_csv = linha[2].split(".")[0]
        if len(block_do_csv.split("_")) > 2:
            block_pre_tratado = block_do_csv.split("_")[0]
            numero_do_bloco = block_do_csv.split("_")[1]
            if block_do_csv.split("_")[2] != "rev":
                numero_do_bloco = block_do_csv.split("_")[2]
            block_do_csv = block_pre_tratado + "_" + numero_do_bloco

        if linha[0] == review_para_trocar and linha[1] == revision_para_trocar and block_do_csv == block_atual:
            linha[2] = block_do_csv + "_rev_" + revision_atual + ".java"
            linhas[i] = [",".join(linha)]  # Atualizar a linha na lista `linhas`

modificar_csv(caminho_type_clones, linhas)

for block in blocks_equals:
    review_atual = block[2].split("_")[0]
    revision_atual = block[2].split("_")[1]
    review_para_trocar = block[0].split("_")[0]
    number_block_atual = block[3].split("_")[1]
    number_block = block[1].split("_")[1]
    revision_para_trocar = block[0].split("_")[1]
    block_atual = block[3]
    block_para_trocar = block[1]
    print(review_atual + " Igualando block da review:" + review_para_trocar + " revision: " + revision_atual + " rev para trocar: " + revision_para_trocar)

    if review_atual == review_para_trocar and number_block_atual != number_block:
        for i in range(1, len(linhas)):  # Iterar a partir da posição 1, ignorando a posição 0
            block_atual = block[3]
            linha = linhas[i][0].split(",")
            block_do_csv = linha[2].split(".")[0]
            if len(block_do_csv.split("_")) > 2:
                block_pre_tratado = block_do_csv.split("_")[0]
                numero_do_bloco = block_do_csv.split("_")[1]
                if block_do_csv.split("_")[2] != "rev":
                    numero_do_bloco = block_do_csv.split("_")[2]
                if block_do_csv.split("_")[2] == "rev":
                    numero_do_bloco = block_do_csv.split("_")[1]
                if block_do_csv.split("_")[2] == "equals":
                    continue
                block_do_csv = block_pre_tratado + "_" + numero_do_bloco

            if linha[0] == review_para_trocar and linha[1] == revision_para_trocar and block_do_csv == block_para_trocar:
                block_atual = block_atual + "_equals_" + revision_atual
                linha[2] = block_atual + ".java"
                linhas[i] = [",".join(linha)]  # Atualizar a linha na lista `linhas`
        modificar_csv(caminho_type_clones, linhas)
    else:
        if review_atual != review_para_trocar:
            print(review_atual, review_para_trocar)

linhas = []
with open(caminho_type_clones, newline='') as csvfile:
    leitor_csv = csv.reader(csvfile, delimiter='\t')
    header = next(leitor_csv)  # Capturar o cabeçalho
    linhas.append(header)  # Adicionar o cabeçalho à lista de linhas
    for linha in leitor_csv:
        linhas.append(linha)

for i in range(1, len(linhas)):  # Iterar a partir da posição 1, ignorando a posição 0
    linha = linhas[i][0].split(",")
    #verifica se o block de revision equal possui um clone, se nao possuir ele deixa o _equals_
    verificador = False
    if len(linha[2].split("_")) > 2:
        if linha[2].split("_")[2] == "equals":
            block_name = linha[2].split("_")[0]
            block_number = linha[2].split("_")[1]
            block = block_name + "_" + block_number
            review = linha[0]
            revision = linha[1]
            revision_equals = linha[2].split("_")[3].split(".")[0]
            for j in range(1, len(linhas)):
                linha_temp = linhas[j][0].split(",")
                block_temp = linha_temp[2]
                if len(linha_temp[2].split("_")) > 2:
                    if linha_temp[2].split("_")[2] == "rev":
                        block_name_temp = linha_temp[2].split("_")[0]
                        block_number_temp = linha_temp[2].split("_")[1]
                        block_temp = block_name_temp + "_" + block_number_temp
                if linha_temp[0] == review and linha_temp[1] == revision_equals and block_temp == block:
                    block = linha_temp[2]
                if linha_temp[0] == review and linha_temp[1] == revision_equals:
                    verificador = True
            if verificador == True:
                linha[2] = block
                linhas[i] = [",".join(linha)]

modificar_csv(caminho_type_clones, linhas)