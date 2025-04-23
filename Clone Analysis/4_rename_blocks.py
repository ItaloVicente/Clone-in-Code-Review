import configparser
import csv as csv_read
import os
import shutil

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
min_line = int(config['DETAILS']['min_clone'])
caminho_do_arquivo = 'metadata/' + PROJECT + '.csv'

if os.path.exists("blocks/" + PROJECT) and os.path.isdir("blocks/" + PROJECT):
    shutil.rmtree("blocks/" + PROJECT)

if not os.path.exists("blocks"):
    os.mkdir("blocks")

if not os.path.exists("blocks/" + PROJECT):
    os.mkdir("blocks/" + PROJECT)


def write_in_java(methods, review, revision, arquivo, number):
    for linha in methods:
        try:
            with open(f"blocks/{PROJECT}/{review}_{revision}/{arquivo}_{number}.java", "a", encoding="utf-8") as java:
                string = linha[0]
                java.write(string[1:] + "\n")
        except UnicodeError:
            with open(f"blocks/{PROJECT}/{review}_{revision}/{arquivo}_{number}.java", "a", encoding="iso-8859-1") as java:
                string = linha[0]
                java.write(string[1:] + "\n")


def create_path(review, revision):
    os.mkdir(f"blocks/{PROJECT}/{review}_{revision}")


with open(caminho_do_arquivo, newline='') as arquivo_csv:
    csv_read.field_size_limit(10 ** 6)
    leitor_csv = csv_read.reader(arquivo_csv)
    cabecalho = next(leitor_csv)

    for linha in leitor_csv:
        methods = []
        lista_temp = []
        block_number = 0

        if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and len(linha) > 10:
            print(f"Analisando review: {linha[1]} rev: {linha[2]}")

            if linha[9] != linha[10]:
                create_path(linha[1], linha[2])
                diff = f"diffs/{PROJECT}/{linha[1]}_{linha[2]}.diff"
                try:
                    with open(diff, "r", encoding='utf-8') as arquivo_diff:
                        leitor = False
                        leitor_diff = csv_read.reader(arquivo_diff)

                        for linha_diff in leitor_diff:
                            if len(linha_diff) > 0:
                                if linha_diff[0].startswith("diff"):
                                    partes = linha_diff[0].split("/")
                                    nome_arquivo_completo = partes[-1]
                                    if '.' in nome_arquivo_completo:
                                        arquivo, extensao = nome_arquivo_completo.rsplit(".", 1)
                                        if extensao == "java":
                                            leitor = True
                                        else:
                                            leitor = False
                                    else:
                                        leitor = False

                            if leitor and len(linha_diff) > 0:
                                if linha_diff[0].startswith("+") and not linha_diff[0].startswith("+++") and not any(
                                        x in linha_diff[0] for x in ["//", "/*", "*/"]) and not linha_diff[0][
                                                                                                1:].lstrip().startswith(
                                        "*"):
                                    lista_temp.append(linha_diff)
                                elif not linha_diff[0].startswith("+"):
                                    if len(lista_temp) >= min_line:
                                        methods = lista_temp
                                        lista_temp = []
                                        write_in_java(methods, linha[1], linha[2], arquivo, block_number)
                                        block_number += 1
                                    else:
                                        lista_temp = []
                                        methods = []
                except UnicodeError:
                    with open(diff, "r", encoding='iso-8859-1') as arquivo_diff:
                        leitor = False
                        leitor_diff = csv_read.reader(arquivo_diff)

                        for linha_diff in leitor_diff:
                            if len(linha_diff) > 0:
                                if linha_diff[0].startswith("diff"):
                                    partes = linha_diff[0].split("/")
                                    nome_arquivo_completo = partes[-1]
                                    if '.' in nome_arquivo_completo:
                                        arquivo, extensao = nome_arquivo_completo.rsplit(".", 1)
                                        if extensao == "java":
                                            leitor = True
                                        else:
                                            leitor = False
                                    else:
                                        leitor = False

                            if leitor and len(linha_diff) > 0:
                                if linha_diff[0].startswith("+") and not linha_diff[0].startswith("+++") and not any(
                                        x in linha_diff[0] for x in ["//", "/*", "*/"]) and not linha_diff[0][
                                                                                                1:].lstrip().startswith(
                                        "*"):
                                    lista_temp.append(linha_diff)
                                elif not linha_diff[0].startswith("+"):
                                    if len(lista_temp) >= min_line:
                                        methods = lista_temp
                                        lista_temp = []
                                        write_in_java(methods, linha[1], linha[2], arquivo, block_number)
                                        block_number += 1
                                    else:
                                        lista_temp = []
                                        methods = []
            else:
                print("Revision sem after")