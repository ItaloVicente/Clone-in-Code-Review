import configparser
import csv

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_new_categorization_multipla = "new categorization/" + PROJECT + "(tipo_multipla)" + ".csv"

with open(caminho_new_categorization_multipla, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    dict_multiple = {}
    count_ambos = 0
    count_recorrente = 0
    count_individual = 0
    for linha in leitor_csv:
        recorrente = 0
        individual = 0
        if linha[2] == "recorrente":
            recorrente = 1
        elif linha[2] == "individual":
            individual=1
        else:
            print(linha)
        if linha[0] in dict_multiple:
            L = dict_multiple[linha[0]]
            if L[0] == 0:
                L[0] = recorrente
            if L[1] == 0:
                L[1] = individual
            dict_multiple[linha[0]] = L
        else:
            L = [recorrente,individual]
            dict_multiple[linha[0]] = L
    for review in dict_multiple:
        L = dict_multiple[review]
        if L[0] == 1 and L[1] == 0:
            count_recorrente+=1
        elif L[0] == 0 and L[1] == 1:
            count_individual+=1
        elif L[0] == 1 and L[1] == 1:
            count_ambos+=1
        else:
            print(review)
print(count_recorrente, count_individual, count_ambos)