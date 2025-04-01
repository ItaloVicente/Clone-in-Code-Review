import configparser
import csv
import os

def analise_revisions(caminho_type_clones):
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)

        antecessor = None
        index_move = 0
        index_test = 0
        index_diff = 0
        revisions = {}
        for linha in leitor_csv:
            atual = linha[0] + "_" + linha[1] + "_" + linha[2].split(".")[0]
            if antecessor is None:
                antecessor = atual

            if atual != antecessor:
                block = antecessor.split("_")[2:]
                block_with_underline = ""
                for i in block:
                    if block_with_underline == "":
                        block_with_underline = i
                    else:
                        block_with_underline = block_with_underline + "_" + i
                revisions[antecessor] = (block_with_underline,index_move, index_test, index_diff, antecessor.split("_")[1])
                # Reset the indices for the next group
                index_move = 0
                index_test = 0
                index_diff = 0
                antecessor = atual

            # Update the indices based on the current line
            if int(linha[3]) == 1:
                index_move = 1
            if int(linha[4]) == 1:
                index_test = 1
            if int(linha[5]) == 1:
                index_diff = 1

        # Handle the last group after the loop
        if antecessor is not None:
            revisions[antecessor] = (antecessor.split("_")[2].split(".")[0],index_move, index_test, index_test, antecessor.split("_")[1])
            #print(antecessor + ": " + str(index_move) + "," + str(index_test) + "," + str(index_diff))
    #print(revisions)
    return revisions


def analise_revisions_with_blocks(caminho_type_clones):
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)

        revisions = {}
        for linha in leitor_csv:
            review = linha[0]
            index_move = int(linha[3])
            index_test = int(linha[4])
            index_diff = int(linha[5])

            if index_move == 0 and index_test == 0:
                temp = [linha[2].split(".")[0], index_move, index_test, index_diff, linha[1]]

                if review in revisions:
                    revisions[review].append(temp)
                else:
                    revisions[review] = [temp]

    return revisions
def write_in_new_categorization_multipla(review,block, categoria, tipo, duracao , distancia, move_block):
    with open("new categorization/" + PROJECT + "(tipo_multipla).csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(
            [review,block,categoria,tipo,duracao,distancia,move_block])
def write_in_new_categorization_unica(review,block, categoria, move_block):
    with open("new categorization/" + PROJECT + "(tipo_unica).csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(
            [review,block,categoria,move_block])
def write_in_csv(review, classification ,status_clone):
    with open("classification/" + PROJECT + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([review, classification, status_clone])
def dict_reviews(revisions):
    antecessor = None
    reviews = {}
    L=[]
    for linha in revisions:
        atual = linha.split("_")[0]
        revision = linha.split("_")[1]
        block = linha.split("_")[2:]
        block_with_underline = ""
        for i in block:
            if block_with_underline == "":
                block_with_underline = i
            else:
                block_with_underline = block_with_underline + "_" + i
        if antecessor is None:
            antecessor = atual

        if atual != antecessor:
            reviews[antecessor] = L
            antecessor = atual
            L = []
        L.append(revisions[antecessor+"_"+revision+"_"+block_with_underline])
    # Handle the last group after the loop
    if antecessor is not None:
        reviews[antecessor] = L
        L = []
    return reviews


def create_review_revision_dict(caminho_do_arquivo):
    review_dict = {}

    with open(caminho_do_arquivo, newline='') as csvfile:
        reader = csv.reader(csvfile)
        next(reader)  # Skip header row

        for row in reader:
            if(len(row)>2):
                review_number = row[1]
                revision_number = int(row[2])

                if review_number in review_dict:
                    review_dict[review_number].append(revision_number)
                else:
                    review_dict[review_number] = [revision_number]
            else:
                print(row)

    return review_dict
def return_reviews_with_clones(caminho_type_clones):
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)
        index_move = 0
        index_test = 0
        index_diff = 0
        reviews = []
        for linha in leitor_csv:
            review = linha[0]
            if int(linha[3]) == 1:
                index_move = 1
            if int(linha[4]) == 1:
                index_test = 1
            if int(linha[5]) == 1:
                index_diff = 1
            if index_move==0 and index_test == 0:
                reviews.append(review)
            index_move = 0
            index_test = 0
            index_diff = 0
    return reviews
config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_do_arquivo = 'metadata/'+ PROJECT +'.csv'
caminho_type_clones = "type_clones/" + PROJECT + ".csv"
if not os.path.exists("classification"):
    os.mkdir("classification")

if not os.path.isfile("classification/" + PROJECT + ".csv"):
    with open("classification/" + PROJECT + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(
            ["review_number", "type_of_revisions", "clone_exists"])
reviews = dict_reviews(analise_revisions(caminho_type_clones))
reviews_with_clone = return_reviews_with_clones(caminho_type_clones)
with open(caminho_do_arquivo, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    reviews_passadas = []
    temp = None
    classification = ""

    for linha in leitor_csv:
        review = linha[1]

        if temp is None:
            temp = review
        else:
            # Caso temp seja igual a review, tratamos como "multipla"
            if temp in reviews and review == temp and temp not in reviews_passadas:
                classification = "multipla"
                if temp in reviews_with_clone:
                    write_in_csv(temp, classification, "review with at least one clone")
                else:
                    write_in_csv(temp, classification, "uninteresting_clone")
            # Se review for diferente de temp e temp estiver em reviews, tratamos como "unica"
            elif review != temp and temp not in reviews_passadas and temp in reviews:
                classification = "unica"
                if temp in reviews_with_clone:
                    write_in_csv(temp, classification, "review with at least one clone")
                else:
                    write_in_csv(temp, classification, "uninteresting_clone")
            # Se temp não estiver em reviews, é considerada "unica" sem clones
            elif review != temp and temp not in reviews_passadas and temp not in reviews:
                classification = "unica"
                write_in_csv(temp, classification, "no_clone")
            # Caso especial para múltiplas revisões sem clones
            elif review == temp and temp not in reviews_passadas and temp not in reviews:
                classification = "multipla"
                write_in_csv(temp, classification, "no_clone")

            # Adicionando temp à lista de revisões já processadas
            reviews_passadas.append(temp)

            # Atualizando temp com o valor da próxima revisão (review)
            temp = review

    # Último caso fora do loop
    if temp in reviews and temp not in reviews_passadas:
        classification = "multipla" if temp in reviews_with_clone else "unica"
        if temp in reviews_with_clone:
            write_in_csv(temp, classification, "review with at least one clone")
        else:
            write_in_csv(temp, classification, "uninteresting_clone" if classification == "multipla" else "no_clone")
    elif temp not in reviews_passadas:
        classification = "unica"
        write_in_csv(temp, classification, "no_clone")

    reviews_passadas.append(temp)

caminho_classification = "classification/" + PROJECT + ".csv"
if not os.path.exists("new categorization"):
    os.mkdir("new categorization")

if not os.path.isfile("new categorization/" + PROJECT + "(tipo_unica)" + ".csv"):
    with open("new categorization/" + PROJECT + "(tipo_unica)" + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["review_number","categoria", "move_block"])
if not os.path.isfile("new categorization/" + PROJECT + "(tipo_multipla)" + ".csv"):
    with open("new categorization/" + PROJECT + "(tipo_multipla)" + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["review_number","block", "categoria","tipo", "duracao", "distancia", "move_block"])
reviews_with_blocks = analise_revisions_with_blocks(caminho_type_clones)
reviews_with_revisions = create_review_revision_dict(caminho_do_arquivo)
with open(caminho_classification, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    reviews_with_more_one_revision = []
    reviews_passadas = []
    for linha in leitor_csv:
        review = linha[0]
        if linha[1] == "multipla" and linha[2] != "no_clone" and linha[2]!= "uninteresting_clone":
            dict_blocks = {}
            for review_with_block in reviews_with_blocks[review]:
                temp = [review_with_block[1], review_with_block[2], review_with_block[3], review_with_block[4]]
                if review_with_block[0] in dict_blocks:
                    dict_blocks[review_with_block[0]].append(temp)
                else:
                    dict_blocks[review_with_block[0]] = [temp]  # Note the change here to initialize as a list of temp
            for block in dict_blocks:
                block_chronology = ""
                block_position = ""
                #verifica se o bloco com mais de um clone e unico
                verificador = False
                #verifica se o bloco seria do tipo apa/des/apa
                verificador2 = False
                last = 1
                revision_anterior = None  # Initialize here
                for situation in dict_blocks[block]:
                    last_clone_block = len(dict_blocks[block])
                    ini_ocorrencia = int(dict_blocks[block][0][3])
                    final_ocorrencia = int(dict_blocks[block][len(dict_blocks[block]) - 1][3])
                    ini_revision = int(reviews_with_revisions[review][0])
                    final_revision = int(reviews_with_revisions[review][len(reviews_with_revisions[review]) - 1])
                    arquive_diff = int(situation[2])
                    revision = int(situation[3])
                    duracao = final_ocorrencia-ini_ocorrencia + 1
                    distancia = ini_ocorrencia/final_revision
                    if ini_ocorrencia==1:
                        distancia=0
                    if revision_anterior is None:
                        revision_anterior = revision

                    if arquive_diff == 1:
                        if block_position == "mesmo arquivo":
                            block_position = "ambos"
                        elif block_position == "":
                            block_position = "arquivo diferente"
                    if arquive_diff == 0:
                        if block_position == "arquivo diferente":
                            block_position = "ambos"
                        elif block_position == "":
                            block_position = "mesmo arquivo"

                    if revision != revision_anterior:
                        verificador = True

                    if (len(dict_blocks[block]) == 1) or (last_clone_block == last and verificador == False):
                        block_chronology = "unique/mei"
                        if revision == ini_revision and revision == revision_anterior:
                            block_chronology = "unique/ini"
                        if revision == final_revision:
                            block_chronology = "unique/final"
                    else:
                        if ini_ocorrencia == ini_revision:
                            block_chronology = "ini"
                        if revision > ini_revision and (revision == revision_anterior + 1 or revision == revision_anterior):
                            if block_chronology == "ini":
                                block_chronology = "ini/mei"
                                if revision == final_revision and block_chronology == "ini/mei":
                                    block_chronology = "ini/mei/final"
                            else:
                                block_chronology = "mei"
                                if revision == final_revision and block_chronology == "mei":
                                    block_chronology = "mei/final"
                        if revision >= ini_revision and revision > (revision_anterior + 1):
                            if block_chronology == "ini/mei" or block_chronology == "mei" or block_chronology == "ini":
                                block_chronology = "apa/des"
                        if revision >= ini_revision and revision > revision_anterior + 1 and block_chronology == "apa/des":
                            block_chronology = "apa/des/apa"
                            verificador2=True
                            write_in_new_categorization_multipla(review,block,"recorrente", block_chronology,None,distancia, block_position)

                    revision_anterior = revision  # Update here
                    last += 1
                if verificador == False:
                    verificador2=True
                    write_in_new_categorization_multipla(review, block, "individual", block_chronology, 1/final_revision, distancia, block_position)
                if verificador2 == False:
                    write_in_new_categorization_multipla(review,block,"recorrente", block_chronology,duracao/final_revision,distancia, block_position)
        if linha[1] == "unica":
            if linha[2]!="no_clone" and linha[2]!= "uninteresting_clone":
                dict_blocks = {}
                for review_with_block in reviews_with_blocks[review]:
                    temp = [review_with_block[1], review_with_block[2], review_with_block[3], review_with_block[4]]
                    if review_with_block[0] in dict_blocks:
                        dict_blocks[review_with_block[0]].append(temp)
                    else:
                        dict_blocks[review_with_block[0]] = [temp]  # Note the change here to initialize as a list of temp
                for block in dict_blocks:
                    block_position = ""
                    for situation in dict_blocks[block]:
                        arquive_diff = int(situation[2])

                        if arquive_diff == 1:
                            if block_position == "mesmo arquivo":
                                block_position = "ambos"
                            elif block_position == "":
                                block_position = "arquivo diferente"
                        if arquive_diff == 0:
                            if block_position == "arquivo diferente":
                                block_position = "ambos"
                            elif block_position == "":
                                block_position = "mesmo arquivo"
                    write_in_new_categorization_unica(review,block, linha[2], block_position)
            else:
                write_in_new_categorization_unica(review, None, linha[2], None)