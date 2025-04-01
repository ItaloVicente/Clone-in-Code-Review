import csv
import matplotlib.pyplot as plt
import numpy as np


def calcular_valores_box_plot(dados):
    dados = sorted(dados)
    print(dados)

    Q1 = np.percentile(dados, 25)
    Q2 = np.median(dados)
    Q3 = np.percentile(dados, 75)


    IQR = Q3 - Q1
    limite_inferior = Q1 - 1.5 * IQR
    limite_superior = Q3 + 1.5 * IQR


    outliers = [x for x in dados if x < limite_inferior or x > limite_superior]

    print(f"Q1: {Q1}")
    print(f"Mediana (Q2): {Q2}")
    print(f"Q3: {Q3}")
    print(f"IQR: {IQR}")
    print(f"Limite Inferior: {limite_inferior}")
    print(f"Limite Superior: {limite_superior}")
    print(f"Outliers: {outliers}")
    return Q1, Q2, Q3, IQR, limite_inferior, limite_superior, outliers

PROJECTs = ["platform.ui", "egit", "jgit", "couchbase-jvm-core", "couchbase-java-client", "spymemcached"]
# Duration
lista_duracao_individual_ini = []
lista_duracao_individual_meio = []
lista_duracao_individual_final = []
lista_duracao_recorrente_ini_mei = []
lista_duracao_recorrente_mei = []
lista_duracao_recorrente_mei_final = []
lista_duracao_recorrente_ini_mei_final = []
lista_duracao_recorrente_apa_des_apa = [0]

# Distance
lista_distancia_individual_ini = []
lista_distancia_individual_meio = []
lista_distancia_individual_final = []
lista_distancia_recorrente_ini_mei = []
lista_distancia_recorrente_mei = []
lista_distancia_recorrente_mei_final = []
lista_distancia_recorrente_ini_mei_final = []
lista_distancia_recorrente_apa_des_apa = []

for PROJECT in PROJECTs:
    caminho = "new categoration/" + PROJECT + "(tipo_multipla)" + ".csv"
    with open(caminho, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)
        for linha in leitor_csv:
            if linha[2] == "recorrente":
                if linha[3] == "ini/mei":
                    lista_distancia_recorrente_ini_mei.append(float(linha[5]))
                    lista_duracao_recorrente_ini_mei.append(float(linha[4]))
                elif linha[3] == "mei":
                    lista_distancia_recorrente_mei.append(float(linha[5]))
                    lista_duracao_recorrente_mei.append(float(linha[4]))
                elif linha[3] == "mei/final":
                    lista_distancia_recorrente_mei_final.append(float(linha[5]))
                    lista_duracao_recorrente_mei_final.append(float(linha[4]))
                elif linha[3] == "ini/mei/final":
                    lista_distancia_recorrente_ini_mei_final.append(float(linha[5]))
                    lista_duracao_recorrente_ini_mei_final.append(float(linha[4]))
                elif linha[3] == "apa/des/apa":
                    lista_distancia_recorrente_apa_des_apa.append(float(linha[5]))

            if linha[2] == "individual":
                if linha[3] == "unique/ini":
                    if float(linha[5]) !=0:
                        print(linha)
                    lista_distancia_individual_ini.append(float(linha[5]))
                    lista_duracao_individual_ini.append(float(linha[4]))
                elif linha[3] == "unique/mei":
                    lista_distancia_individual_meio.append(float(linha[5]))
                    lista_duracao_individual_meio.append(float(linha[4]))
                elif linha[3] == "unique/final":
                    lista_distancia_individual_final.append(float(linha[5]))
                    lista_duracao_individual_final.append(float(linha[4]))

Duracao_individual = {
        "unique/ini":lista_duracao_individual_ini,
        "unique/mei":lista_duracao_individual_meio,
        "unique/final":lista_duracao_individual_final
    }

Duracao_recorrente = {
        "ini/mei": lista_duracao_recorrente_ini_mei,
        "mei": lista_duracao_recorrente_mei,
        "mei/final": lista_duracao_recorrente_mei_final,
        "ini/mei/final": lista_duracao_recorrente_ini_mei_final,
        "apa/des/apa": lista_duracao_recorrente_apa_des_apa
    }

Distancia_individual = {
        "unique/ini":lista_distancia_individual_ini,
        "unique/mei":lista_distancia_individual_meio,
        "unique/final":lista_distancia_individual_final
    }

Distancia_recorrente = {
        "ini/mei": lista_distancia_recorrente_ini_mei,
        "mei": lista_distancia_recorrente_mei,
        "mei/final": lista_distancia_recorrente_mei_final,
        "ini/mei/final": lista_distancia_recorrente_ini_mei_final,
        "apa/des/apa": lista_distancia_recorrente_apa_des_apa
    }

fig, axs = plt.subplots(2, 2, figsize=(14, 10))

    # Box plot de Duração Individual
axs[0, 0].boxplot(Duracao_individual.values(), tick_labels=Duracao_individual.keys(), patch_artist=True)
axs[0, 0].set_title("Duração - Individual")
axs[0, 0].set_ylabel("Duração")
axs[0, 0].grid(axis='y', linestyle='--', alpha=0.7)

    # Box plot de Duração Recorrente
axs[0, 1].boxplot(Duracao_recorrente.values(), tick_labels=Duracao_recorrente.keys(), patch_artist=True)
axs[0, 1].set_title("Duração - Recorrente")
axs[0, 1].set_ylabel("Duração")
axs[0, 1].grid(axis='y', linestyle='--', alpha=0.7)

    # Box plot de Distância Individual
axs[1, 0].boxplot(Distancia_individual.values(), tick_labels=Distancia_individual.keys(), patch_artist=True)
axs[1, 0].set_title("Distância - Individual")
axs[1, 0].set_ylabel("Distância")
axs[1, 0].grid(axis='y', linestyle='--', alpha=0.7)

    # Box plot de Distância Recorrente
axs[1, 1].boxplot(Distancia_recorrente.values(), tick_labels=Distancia_recorrente.keys(), patch_artist=True)
axs[1, 1].set_title("Distância - Recorrente")
axs[1, 1].set_ylabel("Distância")
axs[1, 1].grid(axis='y', linestyle='--', alpha=0.7)

plt.tight_layout()
plt.savefig("box_plots_all_projects.png")  
