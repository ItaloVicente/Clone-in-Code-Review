import configparser
import csv
import os
import subprocess
import shutil
from configobj import ConfigObj
#Elasticsearch need is on
#Java 8
#in the Siamese config files, I need two files, one index and one search, example "index-config.properties" and "search-config.properties"
def write_save_snapshot(review_number,revision_number, revisions_downloads):
    review_number_save = open("save_index/" + PROJECT + ".txt", "w+")
    review_number_save.write(
        review_number + "\t" + revision_number + "\t" + str(revisions_downloads))
    review_number_save.close()

def search_siamese(PROJECT, review, revision, min_clone):
    caminho_arquivo_config = path_to_repo + "/siamese/Siamese/search-config.properties"
    nome_index = PROJECT + "_before_" + review + "_" + revision
    config = ConfigObj(caminho_arquivo_config, list_values=False)

    # Modificar os valores desejados
    config['index'] = nome_index
    config['elasticsearchLoc'] = path_to_elastic
    config['inputFolder'] = path_to_repo + "/blocks/" + PROJECT + "/" + review + "_" + revision
    config['minCloneSize'] = min_clone
    config.write()
    # Salvar as alterações de volta ao arquivo de configuração
    # Construir o comando para executar o JAR
    jar_command = [
        'java',
        '-jar',
        path_to_repo + '/siamese/Siamese/siamese-0.0.6-SNAPSHOT.jar',
        '-cf',
        path_to_repo + '/siamese/Siamese/search-config.properties'
    ]

    # Executar o comando
    subprocess.run(jar_command)

def apagar_conteudo_do_diretorio(diretorio):
    # Certifique-se de que o diretório existe
    if os.path.exists(diretorio):
        # Remove todos os arquivos e pastas dentro do diretório
        for conteudo in os.listdir(diretorio):
            caminho_conteudo = os.path.join(diretorio, conteudo)
            if os.path.isfile(caminho_conteudo) or os.path.islink(caminho_conteudo):
                os.unlink(caminho_conteudo)
            elif os.path.isdir(caminho_conteudo):
                shutil.rmtree(caminho_conteudo)

def read_save():
    review_number_check = open("save_index/" + PROJECT + ".txt", "r")
    L = review_number_check.read().split("\t")
    return L

def apagar_index(PROJECT, review, revision):
    #troque o local host parar o host do seu elasticsearch
    jar_command = ['curl', '-XDELETE',  'localhost:9200/' + PROJECT + "_before_" + review + "_" + revision]
    subprocess.run(jar_command)
def index_siamese(PROJECT, review, revision):
    #altero o caminho para onde está o index-config
    caminho_arquivo_config = path_to_repo + "/siamese/Siamese/index-config.properties"
    nome_index = PROJECT + "_before_" + review + "_" + revision

    # Carregar o arquivo de configuração
    config = ConfigObj(caminho_arquivo_config, list_values=False)

    # Modificar os valores desejados
    config['index'] = nome_index
    config['elasticsearchLoc'] = path_to_elastic
    config['inputFolder'] = path_to_repo + "/befores/" + PROJECT + "/before_" + review + "_" + revision
    config.write()
    # Salvar as alterações de volta ao arquivo de configuração
    # Construir o comando para executar o JAR
    jar_command = [
        'java',
        '-jar',
        path_to_repo + '/siamese/Siamese/siamese-0.0.6-SNAPSHOT.jar',
        '-cf',
        path_to_repo + '/siamese/Siamese/index-config.properties'
    ]

    # Executar o comando
    subprocess.run(jar_command)


# script starts here
# após uma análise é melhor ficar fazendo um por um, pode alterar, mas o algoritmo não salva onde parou o search, só onde estava o index, ou seja, não irá alterar os resultados, mas se o algortimo tiver parado no search, ele irá fazer o search do começo para os befores que estavam na pasta
config = configparser.ConfigParser()
config.read("settings.ini")
path_to_elastic = config['DETAILS']['path_to_elastic']
max_befores = int(config['DETAILS']['max_befores'])
min_clone = int(config['DETAILS']['min_clone'])
PROJECT = config['DETAILS']['project']
path_to_repo = config['DETAILS']['path_to_repo']
caminho_do_arquivo = 'metadata/'+ PROJECT +'.csv'

if os.path.exists("befores") == False:
    os.mkdir("befores")
if os.path.exists("befores/" + PROJECT) == False:
    os.mkdir("befores/" + PROJECT)

if os.path.exists("save_index") == False:
    os.mkdir("save_index")
if os.path.exists("save_index/" + PROJECT + ".txt") == False:
    review_number_save = open("save_index/" + PROJECT + ".txt", "w")
    review_number_save.write("0\t0\t0")
    review_number_save.close()

review_number_check = open("save_index/" + PROJECT + ".txt", "r")
L=review_number_check.read().split("\t")
int_review_number_check = int(L[0])
int_revision_number_check = int(L[1])
revisions_downloads = int(L[2])
review_number_check.close()
diretorio = "befores/" + PROJECT

with open(caminho_do_arquivo, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    # Para pular o cabeçalho, se houver um
    cabeçalho = next(leitor_csv)
    repo_path = "git_repos/" + PROJECT
    origem = "git_repos/" + PROJECT
    for linha in leitor_csv:
        if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and len(linha) > 10:
            verificador1=False
            if int(linha[1]) == int_review_number_check:
                verificador1 = True  # verifica se onde o script parou tem mais de uma revision
            if int(linha[1]) > (int_review_number_check - 1) and verificador1 == False:
                print("Gerando before da review: " + linha[1] + " rev: " + linha[2])
                review = linha[1]
                revision = linha[2]
                destination_path = "befores/" +PROJECT+ "/before_" + review + "_" + revision
                if linha[9] != linha[10]:
                    id_before = linha[9]
                    # Comando git para realizar o checkout do commit especificado
                    checkout_command = f"git -C {repo_path} checkout {id_before}"

                    # Executa o comando git checkout usando subprocess
                    subprocess.run(checkout_command, shell=True)
                    arquivos = os.listdir(origem)
                    if os.path.exists(destination_path) == True:
                        shutil.rmtree(destination_path)
                    # Copia cada arquivo para a pasta de destino
                    for conteudo in os.listdir(origem):
                        caminho_origem = os.path.join(origem, conteudo)
                        caminho_destino = os.path.join(destination_path, conteudo)

                        # Exclui a pasta .git da cópia
                        if conteudo == ".git":
                            continue

                        if os.path.isfile(caminho_origem):
                            # Verifica se o diretório de destino existe; se não, cria-o
                            os.makedirs(os.path.dirname(caminho_destino), exist_ok=True)
                            # Se for um arquivo, use shutil.copy2
                            shutil.copy2(caminho_origem, caminho_destino)
                        elif os.path.isdir(caminho_origem):
                            # Se for um diretório, use shutil.copytree
                            shutil.copytree(caminho_origem, caminho_destino, symlinks=True)
                    revisions_downloads += 1
                    write_save_snapshot(review,revision,revisions_downloads)
                if revisions_downloads >= max_befores:
                    pasta_desejada = "befores/" + PROJECT
                    diretorios = [nome for nome in os.listdir(pasta_desejada) if os.path.isdir(os.path.join(pasta_desejada, nome))]
                    for nome in sorted(diretorios):
                        L_splitted = nome.split("_")
                        review_for_index = L_splitted[1]
                        revision_for_index = L_splitted[2]
                        index_siamese(PROJECT, review_for_index, revision_for_index)
                        search_siamese(PROJECT, review_for_index, revision_for_index, min_clone)
                        apagar_index(PROJECT, review_for_index, revision_for_index)
                    apagar_conteudo_do_diretorio(diretorio)
                    L = read_save()
                    review_save = L[0]
                    revision_save = L[1]
                    write_save_snapshot(review_save, revision_save, str(0))
                    revisions_downloads=0
            elif int(linha[1]) == int_review_number_check and verificador1 == True:
                if int(linha[2]) > int_revision_number_check:
                    print("Gerando before da review: " + linha[1] + " rev: " + linha[2])
                    review = linha[1]
                    revision = linha[2]
                    destination_path = "befores/" + PROJECT + "/before_" + review + "_" + revision
                    if linha[9] != linha[10]:
                        id_before = linha[9]
                        # Comando git para realizar o checkout do commit especificado
                        checkout_command = f"git -C {repo_path} checkout {id_before}"

                        # Executa o comando git checkout usando subprocess
                        subprocess.run(checkout_command, shell=True)
                        arquivos = os.listdir(origem)
                        if os.path.exists(destination_path) == True:
                            shutil.rmtree(destination_path)

                        # Copia cada arquivo para a pasta de destino
                        for conteudo in os.listdir(origem):
                            caminho_origem = os.path.join(origem, conteudo)
                            caminho_destino = os.path.join(destination_path, conteudo)

                            # Exclui a pasta .git da cópia
                            if conteudo == ".git":
                                continue

                            if os.path.isfile(caminho_origem):
                                # Se for um arquivo, use shutil.copy2
                                shutil.copy2(caminho_origem, caminho_destino)
                            elif os.path.isdir(caminho_origem):
                                # Se for um diretório, use shutil.copytree
                                shutil.copytree(caminho_origem, caminho_destino, symlinks=True)
                        revisions_downloads += 1
                        write_save_snapshot(review, revision, revisions_downloads)
                    if revisions_downloads >= max_befores:
                        pasta_desejada = "befores/" + PROJECT
                        diretorios = [nome for nome in os.listdir(pasta_desejada) if os.path.isdir(os.path.join(pasta_desejada, nome))]
                        for nome in sorted(diretorios):
                            L_splitted = nome.split("_")
                            review_for_index = L_splitted[1]
                            revision_for_index = L_splitted[2]
                            index_siamese(PROJECT, review_for_index, revision_for_index)
                            search_siamese(PROJECT, review_for_index, revision_for_index, min_clone)
                            apagar_index(PROJECT, review_for_index, revision_for_index)
                        apagar_conteudo_do_diretorio(diretorio)
                        L = read_save()
                        review_save = L[0]
                        revision_save = L[1]
                        write_save_snapshot(review_save,revision_save,str(0))
                        revisions_downloads=0
if revisions_downloads > 0:
    pasta_desejada = path_to_repo + "befores/" + PROJECT
    diretorios = [nome for nome in os.listdir(pasta_desejada) if os.path.isdir(os.path.join(pasta_desejada, nome))]
    for nome in sorted(diretorios):
        L_splitted = nome.split("_")
        review_for_index = L_splitted[1]
        revision_for_index = L_splitted[2]
        index_siamese(PROJECT , review_for_index , revision_for_index)
        search_siamese(PROJECT, review_for_index, revision_for_index, min_clone)
        apagar_index(PROJECT, review_for_index, revision_for_index)
    apagar_conteudo_do_diretorio(diretorio)
    L = read_save()
    review_save = L[0]
    revision_save = L[1]
    write_save_snapshot(review_save,revision_save,str(0))
    revisions_downloads=0
