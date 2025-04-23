import configparser
import csv
import os


#Dita até onde os clones vão na review
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
def write_in_review_analise(review,block,block_chronology,block_position):
    with open("review analysis/" + PROJECT + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(
            [review,block,block_chronology,block_position])
def write_in_csv(review, status_clone):
    with open("separate reviews/" + PROJECT + ".csv", "a", newline='') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow([review, status_clone])
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
            if len(row) > 10:
                review_number = row[1]
                revision_number = int(row[2])

                if review_number in review_dict:
                    review_dict[review_number].append(revision_number)
                else:
                    review_dict[review_number] = [revision_number]

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
if not os.path.exists("separate reviews"):
    os.mkdir("separate reviews")

if not os.path.isfile("separate reviews/" + PROJECT + ".csv"):
    with open("separate reviews/" + PROJECT + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["review_number", "clone_exists"])
reviews = dict_reviews(analise_revisions(caminho_type_clones))
reviews_with_clone = return_reviews_with_clones(caminho_type_clones)
with open(caminho_do_arquivo, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    reviews_with_more_one_revision = []
    reviews_passadas = []
    for linha in leitor_csv:
        if linha[1] in reviews and linha[1] not in reviews_with_more_one_revision:
            verificador = False
            review = linha[1]
            if review in reviews_with_clone:
                verificador = True
            if verificador == True:
                write_in_csv(linha[1], "review with at least one clone")
            else:
                write_in_csv(linha[1], "uninteresting_clone")
        elif linha[1] not in reviews_passadas:
            write_in_csv(linha[1],"no_clone")
        reviews_with_more_one_revision.append(linha[1])
        reviews_passadas.append(linha[1])
caminho_separate_reviews = "separate reviews/" + PROJECT + ".csv"
if not os.path.exists("review analysis"):
    os.mkdir("review analysis")

if not os.path.isfile("review analysis/" + PROJECT + ".csv"):
    with open("review analysis/" + PROJECT + ".csv", "w", newline='') as csv_file:
        writer = csv.writer(csv_file)
        #apareceu no comeco, meio ou fim, ou apareceu e sumiu, tipo pode ser qualquer um dos 3, inclusive com intersecao, ou nenhum dos 3
        writer.writerow(
            ["review_number","block", "block_chronology", "block_position"])
reviews_with_blocks = analise_revisions_with_blocks(caminho_type_clones)
reviews_with_revisions = create_review_revision_dict(caminho_do_arquivo)
with open(caminho_separate_reviews, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    cabeçalho = next(leitor_csv)
    reviews_with_more_one_revision = []
    reviews_passadas = []
    for linha in leitor_csv:
        review = linha[0]
        if linha[1] == "review with at least one clone":
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
                        if revision == final_revision == ini_revision == len(reviews_with_revisions[review]):
                            block_chronology = "unique/ini/mei/final"
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
                            write_in_review_analise(review, block, block_chronology, block_position)

                    revision_anterior = revision  # Update here
                    last += 1
                if verificador2 == False:
                    write_in_review_analise(review, block, block_chronology, block_position)
        #else:
            #caso em que não há clones na review
#teste para o tipo apa/des/apa, quantos aparecem, desaparecem e aparecem
def check_sequential_revisions(file_path):
    with open(file_path, mode='r') as file:
        reader = csv.reader(file)
        next(reader)  # Skip header row if it exists

        revisions = {}
        errors = []

        for row in reader:
            review_number = int(row[0])
            revision_number = int(row[1])

            if review_number in revisions:
                previous_revision_number = revisions[review_number][-1]
                if revision_number > previous_revision_number + 1:
                    errors.append(
                        f"Review number {review_number} has a jump in revision number from {previous_revision_number} to {revision_number}")
            else:
                revisions[review_number] = []

            revisions[review_number].append(revision_number)

        return errors

errors = check_sequential_revisions(caminho_type_clones)

if errors:
    for error in errors:
        print(error)
else:
    print("Todos os revision numbers estão em ordem sequencial ou são repetidos conforme esperado.")