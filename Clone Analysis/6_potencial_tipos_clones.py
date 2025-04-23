import configparser
import csv
import os
import re
import pandas as pd
config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_do_arquivo = 'metadata/' + PROJECT + '.csv'


def verificar_e_remover_arquivo_vazio(caminho_arquivo):
    if os.path.exists(caminho_arquivo):
        tamanho_arquivo = os.path.getsize(caminho_arquivo)
        if tamanho_arquivo == 0:
            os.remove(caminho_arquivo)
            print(f"O arquivo {caminho_arquivo} estava vazio e foi removido.")
        else:
            print(f"O arquivo {caminho_arquivo} não está vazio.")
    else:
        print(f"O arquivo {caminho_arquivo} não existe.")


def listar_arquivos_java(diretorio):
    arquivos_java = []
    if os.path.exists(diretorio) and os.path.isdir(diretorio):
        for arquivo in os.listdir(diretorio):
            if arquivo.endswith('.java'):
                arquivos_java.append(arquivo)
    return arquivos_java


def listar_arquivos_com_prefixo(diretorio, PROJECT):
    arquivos_java = []
    if os.path.exists(diretorio) and os.path.isdir(diretorio):
        for arquivo in os.listdir(diretorio):
            if arquivo.startswith(PROJECT):
                arquivos_java.append(arquivo)
    return arquivos_java


def write_in_csv(review, revision, arquivo_positivo, deslocamento, teste, diferente):
    with open("type_clones/" + PROJECT + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([review, revision, arquivo_positivo, deslocamento, teste, diferente])


if not os.path.exists("type_clones"):
    os.mkdir("type_clones")

if not os.path.isfile("type_clones/" + PROJECT + ".csv"):
    with open("type_clones/" + PROJECT + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(
            ["review_number", "revision_number", "arquivo_positivo", "potencial_deslocamento", "potencial_teste",
             "arquivo_diferente"])


def tentar_abrir_arquivo(caminho_arquivo, codificacoes=['utf-8', 'latin-1', 'cp1252']):
    for codificacao in codificacoes:
        try:
            with open(caminho_arquivo, 'r', encoding=codificacao) as arquivo:
                return arquivo.read()
        except UnicodeDecodeError:
            continue
    raise UnicodeDecodeError(f"Não foi possível ler o arquivo {caminho_arquivo} com as codificações fornecidas.")


def csv_nao_vazio(caminho_arquivo):
    try:
        with open(caminho_arquivo, 'r', encoding='utf-8') as f:
            for i, line in enumerate(f):
                if i > 0 or line.strip():
                    return True
        return False
    except Exception as e:
        print(f"Erro ao ler {caminho_arquivo}: {e}")
        return False


def listar_csvs_nao_vazios(diretorio):
    arquivos_nao_vazios = []
    for nome_arquivo in os.listdir(diretorio):
        caminho_arquivo = os.path.join(diretorio, nome_arquivo)
        if os.path.isfile(caminho_arquivo) and nome_arquivo.endswith('.csv'):
            if csv_nao_vazio(caminho_arquivo):
                arquivos_nao_vazios.append(nome_arquivo)
    return arquivos_nao_vazios


# Exemplo de uso
diretorio = 'search_results'
csvs_nao_vazios = listar_csvs_nao_vazios(diretorio)
#print("Arquivos CSV não vazios:", csvs_nao_vazios)

# PROJECT_before_review_revision
arquivos_csv = sorted(listar_arquivos_com_prefixo("search_results/", PROJECT))
with open(caminho_do_arquivo, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)

    for linha in leitor_csv:
        if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and len(linha) > 10:
            string = PROJECT + "_before_" + linha[1] + "_" + linha[2]
            verificador = any(string in clone for clone in csvs_nao_vazios)

            if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and verificador:
                print("Analisando review: " + linha[1] + " rev: " + linha[2])

                caminho_block_positive = "blocks/" + PROJECT + "/" + linha[1] + "_" + linha[2]
                caminho_block_negative = "blocks_negatives/" + PROJECT + "/" + linha[1] + "_" + linha[2]
                arquivos_java_positive = listar_arquivos_java(caminho_block_positive)
                arquivos_java_negative = listar_arquivos_java(caminho_block_negative)

                for arquivo_positive in arquivos_java_positive:
                    caminho_arquive_positive = "blocks/" + PROJECT + "/" + linha[1] + "_" + linha[
                        2] + "/" + arquivo_positive
                    texto_do_arquivo_positivo = tentar_abrir_arquivo(caminho_arquive_positive).replace("\t", "")
                    deslocamento = 0
                    teste = 0
                    diferente = 0

                    for arquivo_negative in arquivos_java_negative:
                        caminho_arquive_negative = "blocks_negatives/" + PROJECT + "/" + linha[1] + "_" + linha[
                            2] + "/" + arquivo_negative
                        texto_do_arquivo_negativo = tentar_abrir_arquivo(caminho_arquive_negative).replace("\t", "")
                        if texto_do_arquivo_positivo in texto_do_arquivo_negativo or texto_do_arquivo_negativo in texto_do_arquivo_positivo:
                            deslocamento = 1

                    if any(x in arquivo_positive for x in ["Test", "test", "TEST", "Tests", "tests"]):
                        teste = 1

                    for arquivo_csv in arquivos_csv:
                        arquivo_csv_splitted = arquivo_csv.split("_")
                        review_csv = arquivo_csv_splitted[2]
                        revision_csv = arquivo_csv_splitted[3]

                        if review_csv == linha[1] and revision_csv == linha[2]:
                            with open("search_results/" + arquivo_csv, newline='') as csv_aberto:
                                leitor_csv_aberto = csv.reader(csv_aberto)

                                for linha_csv_aberto in leitor_csv_aberto:
                                    for i in range(1, len(linha_csv_aberto)):
                                        number_block_csv = linha_csv_aberto[0].split("_")[0].split(".")[0].split("block")[1]
                                        number_block_positive = arquivo_positive.split("_")[1].split(".")[0]
                                        metodo_arquivo_positivo = arquivo_positive.split("_")[0]

                                        if number_block_csv == number_block_positive:
                                            metodo_clone_csv = linha_csv_aberto[i].split("/")[-1].split(".")[0]

                                            if metodo_arquivo_positivo != metodo_clone_csv:
                                                diferente = 1
                                                if any(x in metodo_clone_csv for x in
                                                       ["Test", "test", "TEST", "Tests", "tests"]):
                                                    teste = 1
                                            write_in_csv(linha[1], linha[2], arquivo_positive, deslocamento, teste,
                                                         diferente)