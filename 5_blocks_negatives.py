import configparser
import csv as csv_read
import os
config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
min_line = int(config['DETAILS']['min_clone'])
caminho_do_arquivo = 'metadata/'+PROJECT+'.csv'

if os.path.exists("blocks_negatives") == False:
    os.mkdir("blocks_negatives")

if os.path.exists("blocks_negatives/" + PROJECT) == False:
    os.mkdir("blocks_negatives/"+PROJECT)

def write_in_java(methods,number, review, revision, arquivo):
    for linha in methods:
        try:
            java = open("blocks_negatives/" + PROJECT + "/" + review + "_" + revision + "/"+ arquivo + "_" + str(number) + ".java", "a", encoding="utf-8")
            if len(linha)==1:
                string = linha[0]
                java.write(string[1:] + "\n")
            elif len(linha)>1:
                for count, value in enumerate (linha):
                    if count == 0:
                        java.write(value[1:] + ",")
                    elif count == len(linha)-1:
                        java.write(value + "\n")
                    else:
                        java.write(value + ",")
            java.close()
        except UnicodeError:
            java = open("blocks/" + PROJECT + "/" + review + "_" + revision + "/block" + str(number) + ".java", "a",encoding="iso-8859-1")
            if len(linha) > 0:
                string = linha[0]
                java.write(string[1:] + "\n")
            java.close()

def create_path(review, revision):
    os.mkdir("blocks_negatives/"+PROJECT+"/" + review + "_" + revision)

#script start here
with open(caminho_do_arquivo, newline='') as arquivo_csv:
    csv_read.field_size_limit(10 ** 6)
    leitor_csv = csv_read.reader(arquivo_csv)
    # Para pular o cabeçalho, se houver um
    cabeçalho = next(leitor_csv)
    for linha in leitor_csv:
        methods = []
        lista_temp = []
        number = 0
        if linha[0] != "Review Unable to Download " and linha[0] != "Not Found review: " and len(linha) > 10:
            print("Analisando review: " + linha[1] + " rev: " + linha[2])
            if linha[9] != linha[10]:
                create_path(linha[1],linha[2])
                diff = "diffs/"+ PROJECT + "/" + linha[1] + "_" + linha[2] + ".diff"
                try:
                    with open(diff, "r",  encoding='utf-8') as arquivo_diff:
                        #leitor responsavel para verificar se a parte do diff corresponde a um arquivo .java
                        leitor = False
                        leitor_diff = csv_read.reader(arquivo_diff)
                        for linha_diff in leitor_diff:
                            if len(linha_diff)>0:
                                if(linha_diff[0][0:4] == "diff"):
                                    linha_diff_splitted = linha_diff[0].split("/")
                                    if len(linha_diff_splitted[len(linha_diff_splitted) -1].split("."))>1:
                                        tipo_arquivo = linha_diff_splitted[len(linha_diff_splitted)-1].split(".")[1]
                                        arquivo = linha_diff_splitted[len(linha_diff_splitted)-1].split(".")[0]
                                    else:
                                        leitor = False
                                    if tipo_arquivo == "java":
                                        leitor = True
                                    else:
                                        leitor = False
                            if leitor == True:
                                if len(linha_diff) > 0:
                                    if linha_diff[0][0] == "-" and linha_diff[0][0:3] != "---" and ("//" not in linha_diff[0]):
                                        lista_temp.append(linha_diff)
                                    elif linha_diff[0][0] != "-":
                                        if len(lista_temp)>=min_line:
                                            methods = lista_temp
                                            lista_temp=[]
                                            write_in_java(methods, number, linha[1], linha[2], arquivo)
                                            methods=[]
                                            number+=1
                                        else:
                                            lista_temp=[]
                                            methods=[]
                except UnicodeError:
                    with open(diff, "r",  encoding='iso-8859-1') as arquivo_diff:
                        # leitor responsavel para verificar se a parte do diff corresponde a um arquivo .java
                        leitor = False
                        leitor_diff = csv_read.reader(arquivo_diff)
                        for linha_diff in leitor_diff:
                            if len(linha_diff) > 0:
                                if (linha_diff[0][0:4] == "diff"):
                                    linha_diff_splitted = linha_diff[0].split("/")
                                    if len(linha_diff_splitted[len(linha_diff_splitted) - 1].split(".")) > 1:
                                        tipo_arquivo = linha_diff_splitted[len(linha_diff_splitted) - 1].split(".")[1]
                                        arquivo = linha_diff_splitted[len(linha_diff_splitted) - 1].split(".")[0]
                                    else:
                                        leitor = False
                                    if tipo_arquivo == "java":
                                        leitor = True
                                    else:
                                        leitor = False
                            if leitor == True:
                                if len(linha_diff) > 0:
                                    if linha_diff[0][0] == "-" and linha_diff[0][0:3] != "---" and ("//" not in linha_diff[0]):
                                        lista_temp.append(linha_diff)
                                    elif linha_diff[0][0] != "-":
                                        if len(lista_temp) >= min_line:
                                            methods = lista_temp
                                            lista_temp = []
                                            write_in_java(methods, number, linha[1], linha[2], arquivo)
                                            methods = []
                                            number += 1
                                        else:
                                            lista_temp = []
                                            methods = []
            else:
                print("Revision sem after")

