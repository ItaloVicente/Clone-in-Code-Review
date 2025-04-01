import configparser
import csv
import os

def write_in_csv(type_revisions, reviews):
    with open("count_new_categorization/" + PROJECT + "(type_revisions)" + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([type_revisions, reviews])
def write_in_csv_unica(type_clone, reviews, blocks):
    with open("count_new_categorization/" + PROJECT + "(type_unique)" + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([type_clone, reviews, blocks])
def write_in_csv_multipla(type_clone, reviews, blocks):
    with open("count_new_categorization/" + PROJECT + "(type_multiple)" + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([type_clone, reviews, blocks])

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_new_categorization_multipla = "new categorization/" + PROJECT + "(tipo_multipla)" + ".csv"
caminho_new_categorization_unica = "new categorization/" + PROJECT + "(tipo_unica)" + ".csv"
caminho_new_categorization = "classification/" + PROJECT + ".csv"

if not os.path.exists("count_new_categorization"):
    os.mkdir("count_new_categorization")

if not os.path.isfile("count_new_categorization/" + PROJECT + "(type_revisions)" + ".csv"):
    with open("count_new_categorization/" + PROJECT + "(type_revisions)" + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["type_revisions", "reviews"])

if not os.path.isfile("count_new_categorization/" + PROJECT + "(type_unique)" + ".csv"):
    with open("count_new_categorization/" + PROJECT + "(type_unique)" + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["type_clone", "reviews", "blocks"])

if not os.path.isfile("count_new_categorization/" + PROJECT + "(type_multiple)" + ".csv"):
    with open("count_new_categorization/" + PROJECT + "(type_multiple)" + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["type_clone", "reviews", "blocks"])

with open(caminho_new_categorization, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    unica = 0
    multipla = 0
    total = 0
    for linha in leitor_csv:
        if linha[1] == "unica":
            unica+=1
        if linha[1] == "multipla":
            multipla+=1
        total+=1
    write_in_csv("unicas", unica)
    write_in_csv("multiplas", multipla)
    write_in_csv("total", total)

with open(caminho_new_categorization_unica, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    no_clone_review = 0
    clone_irrelevante_review = 0
    clone_relevante_review = 0
    clone_irrelevante_block = 0
    clone_relevante_block = 0
    L = []
    dict_reviews = {}
    blocks = 0
    int_review = 0
    for linha in leitor_csv:
        if linha[2] == "uninteresting_clone":
            clone_irrelevante_block+=1
        elif linha[2] == "review with at least one clone":
            clone_relevante_block+=1
        else:
            print(linha[0], linha[2])
        if linha[0] not in L:
            L.append(linha[0])
            dict_reviews[linha[0]] = [linha[2]]
        else:
            L_review = dict_reviews[linha[0]]
            if linha[2] not in L_review:
                L_review.append(linha[2])
            dict_reviews[linha[0]] = L_review
        blocks += 1
    for review in dict_reviews:
        if "review with at least one clone" in dict_reviews[review]:
            clone_relevante_review+=1
        if "uninteresting_clone" in dict_reviews[review]:
            clone_irrelevante_review+=1
        if "no_clone" in dict_reviews[review]:
            no_clone_review+=1
        int_review+=1
    write_in_csv_unica("no_clone", no_clone_review, "")
    write_in_csv_unica("irrelevantes", clone_irrelevante_review, clone_irrelevante_block)
    write_in_csv_unica("relevantes", clone_relevante_review, clone_relevante_block)
    write_in_csv_unica("total", int_review, blocks)


def normalize_string(s):
    # Remove espaços extras e normaliza para evitar problemas de comparações
    return s.strip().lower()


with open(caminho_new_categorization_multipla, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)

    unique_ini = 0
    unique_mei = 0
    unique_final = 0
    mei = 0
    ini_mei = 0
    ini_mei_final = 0
    mei_final = 0
    apa_des_apa = 0

    unique_ini_blocks = 0
    unique_mei_blocks = 0
    unique_final_blocks = 0
    mei_blocks = 0
    ini_mei_blocks = 0
    ini_mei_final_blocks = 0
    mei_final_blocks = 0
    apa_des_apa_blocks = 0

    L = []
    dict_reviews = {}
    blocks = 0

    for linha in leitor_csv:
        # Normalizando a string da quarta coluna
        categoria = normalize_string(linha[3])

        if categoria == "unique/ini":
            unique_ini_blocks += 1
        elif categoria == "unique/mei":
            unique_mei_blocks += 1
        elif categoria == "unique/final":
            unique_final_blocks += 1
        elif categoria == "mei":
            mei_blocks += 1
        elif categoria == "ini/mei":
            ini_mei_blocks += 1
        elif categoria == "ini/mei/final":
            ini_mei_final_blocks += 1
        elif categoria == "mei/final":
            mei_final_blocks += 1
        elif categoria == "apa/des/apa":
            apa_des_apa_blocks += 1
        else:
            print(linha[0], linha[3])

        # Adicionando revisões ao dicionário
        if linha[0] not in L:
            L.append(linha[0])
            dict_reviews[linha[0]] = [categoria]
        else:
            L_review = dict_reviews[linha[0]]
            if categoria not in L_review:
                L_review.append(categoria)
            dict_reviews[linha[0]] = L_review

        blocks += 1

    # Contabilizando as categorias
    for review in dict_reviews:
        categorias = dict_reviews[review]

        if "unique/ini" in categorias:
            unique_ini += 1
        if "unique/mei" in categorias:
            unique_mei += 1
        if "unique/final" in categorias:
            unique_final += 1
        if "ini/mei" in categorias:
            ini_mei += 1
        if "mei" in categorias:
            mei += 1
        if "mei/final" in categorias:
            mei_final += 1
        if "ini/mei/final" in categorias:
            ini_mei_final += 1
        if "apa/des/apa" in categorias:
            apa_des_apa += 1

    # Função write_in_csv_multipla deve ser definida ou já existir
    write_in_csv_multipla("unique_ini", unique_ini, unique_ini_blocks)
    write_in_csv_multipla("unique_mei", unique_mei, unique_mei_blocks)
    write_in_csv_multipla("unique_final", unique_final, unique_final_blocks)
    write_in_csv_multipla("ini_mei", ini_mei, ini_mei_blocks)
    write_in_csv_multipla("mei", mei, mei_blocks)
    write_in_csv_multipla("mei_final", mei_final, mei_final_blocks)
    write_in_csv_multipla("ini_mei_final", ini_mei_final, ini_mei_final_blocks)
    write_in_csv_multipla("apa_des_apa", apa_des_apa, apa_des_apa_blocks)
    write_in_csv_multipla("total_reviews_with_clone", len(L), "")
    write_in_csv_multipla("total_blocks_with_clone", "", blocks)
