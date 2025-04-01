import configparser
import csv as csv_read
import os

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
min_line = int(config['DETAILS']['min_clone'])
caminho_do_arquivo = 'metadata/'+PROJECT+'.csv'

if not os.path.exists("blocks"):
    os.mkdir("blocks")

if not os.path.exists("blocks/" + PROJECT):
    os.mkdir("blocks/"+PROJECT)

def write_in_java(methods, number, review, revision):
    for linha in methods:
        for encoding in encodings:
            try:
                with open("blocks/" + PROJECT + "/" + review + "_" + revision + "/block" + str(number) + ".java", "a", encoding=encoding) as java:
                    string = linha[0]
                    java.write(string[1:] + "\n")
                break  # Exit the loop if successful
            except UnicodeError:
                continue  # Try the next encoding

def create_path(review, revision):
    os.mkdir("blocks/"+PROJECT+"/" + review + "_" + revision)

encodings = ["utf-8", "iso-8859-1", "latin1", "windows-1252", "ascii", "utf-16", "utf-32",
             "cp1250", "cp1251", "cp1253", "cp1254", "cp1255", "cp1256",
             "Big5", "GB2312", "Shift_JIS", "EUC-JP", "KOI8-R", "MacRoman"]

# script starts here
with open(caminho_do_arquivo, newline='') as arquivo_csv:
    csv_read.field_size_limit(10 ** 6)
    leitor_csv = csv_read.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    for linha in leitor_csv:
        methods = []
        lista_temp = []
        number = 0
        if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and len(linha) > 10:
            print("Analisando review: " + linha[1] + " rev: " + linha[2])
            if linha[9] != linha[10]:
                create_path(linha[1], linha[2])
                diff = "diffs/" + PROJECT + "/" + linha[1] + "_" + linha[2] + ".diff"
                for encoding in encodings:
                    try:
                        with open(diff, "r", encoding=encoding) as arquivo_diff:
                            leitor = False
                            for linha_diff in arquivo_diff:
                                linha_diff = linha_diff.rstrip("\n")

                                if linha_diff.startswith("diff"):
                                    linha_diff_splitted = linha_diff.split("/")
                                    if len(linha_diff_splitted[-1].split(".")) > 1:
                                        tipo_arquivo = linha_diff_splitted[-1].split(".")[1]
                                    else:
                                        leitor = False
                                    if tipo_arquivo == "java":
                                        leitor = True
                                    else:
                                        leitor = False

                                if leitor:
                                    if linha_diff.startswith("+") and not linha_diff.startswith("+++") and not any(linha_diff[1:].lstrip(" \t").startswith(x) for x in ["//", "/*", "*/"]) and not linha_diff[1:].lstrip(" \t").startswith("*"):
                                        lista_temp.append([linha_diff])

                                    elif not linha_diff.startswith("+"):
                                        if len(lista_temp) >= min_line:
                                            methods = lista_temp
                                            lista_temp = []
                                            write_in_java(methods, number, linha[1], linha[2])
                                            methods = []
                                            number += 1
                                        else:
                                            lista_temp = []
                                            methods = []
                        break  # Exit the loop if successful
                    except (UnicodeError, csv_read.Error):
                        continue  # Try the next encoding
            else:
                print("Revision sem after")
