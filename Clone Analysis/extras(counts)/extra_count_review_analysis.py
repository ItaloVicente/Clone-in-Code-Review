import configparser
import csv
import os

def write_in_csv(type_clone, reviews, blocks):
    with open("count_review_analysis/" + PROJECT + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([type_clone, reviews, blocks])

config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_do_arquivo = 'metadata/'+ PROJECT +'.csv'
caminho_type_clones = "type_clones/" + PROJECT + ".csv"
caminho_review_analysis = "review analysis/" + PROJECT + ".csv"
if not os.path.exists("count_review_analysis"):
    os.mkdir("count_review_analysis")

if not os.path.isfile("count_review_analysis/" + PROJECT + ".csv"):
    with open("count_review_analysis/" + PROJECT + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["type_clone", "reviews", "blocks"])

with open(caminho_review_analysis, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)

    unique_ini = 0
    unique_mei = 0
    unique_final = 0
    mei = 0
    unique = 0
    ini_mei = 0
    ini_mei_final = 0
    mei_final = 0
    apa_des_apa = 0
    unique_ini_blocks = 0
    unique_mei_blocks = 0
    unique_final_blocks = 0
    mei_blocks = 0
    unique_blocks = 0
    ini_mei_blocks = 0
    ini_mei_final_blocks = 0
    mei_final_blocks = 0
    apa_des_apa_blocks = 0
    L = []
    dict_reviews = {}
    blocks = 0
    for linha in leitor_csv:
        if linha[2] == "unique/ini":
            unique_ini_blocks+=1
        elif linha[2] == "unique/mei":
            unique_mei_blocks+=1
        elif linha[2] == "unique/final":
            unique_final_blocks+=1
        elif linha[2] == "mei":
            mei_blocks+=1
        elif linha[2] == "unique/ini/mei/final":
            unique_blocks+=1
        elif linha[2] == "ini/mei":
            ini_mei_blocks+=1
        elif linha[2] == "ini/mei/final":
            ini_mei_final_blocks+=1
        elif linha[2] == "mei/final":
            mei_final_blocks+=1
        elif linha[2] == "apa/des/apa":
            apa_des_apa_blocks+=1
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
        blocks+=1
    for review in dict_reviews:
        print(review, dict_reviews[review])
        if "unique/ini" in dict_reviews[review]:
            unique_ini+=1
        if "unique/mei" in dict_reviews[review]:
            unique_mei+=1
        if "unique/final" in dict_reviews[review]:
            unique_final+=1
        if "unique/ini/mei/final" in dict_reviews[review]:
            unique+=1
        if "ini/mei" in dict_reviews[review]:
            ini_mei+=1
        if "mei" in dict_reviews[review]:
            mei+=1
        if "mei/final" in dict_reviews[review]:
            mei_final+=1
        if "ini/mei/final" in dict_reviews[review]:
            ini_mei_final+=1
        if "apa/des/apa" in dict_reviews[review]:
            apa_des_apa+=1
        else:
            print(review, dict_reviews[review])
    write_in_csv("unique_ini", unique_ini, unique_ini_blocks)
    write_in_csv("unique_mei", unique_mei, unique_mei_blocks)
    write_in_csv("unique_final", unique_final, unique_final_blocks)
    write_in_csv("unique_ini_mei_final", unique, unique_blocks)
    write_in_csv("ini_mei", ini_mei, ini_mei_blocks)
    write_in_csv("mei", mei, mei_blocks)
    write_in_csv("mei_final", mei_final, mei_final_blocks)
    write_in_csv("ini_mei_final", ini_mei_final, ini_mei_final_blocks)
    write_in_csv("apa_des_apa", apa_des_apa, apa_des_apa_blocks)
    write_in_csv("total_reviews_with_clone", len(L), "")
    write_in_csv("total_blocks_with_clone", "",blocks)
