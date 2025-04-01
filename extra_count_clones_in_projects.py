import csv

#Script para contar quantos blocos de clone cada projeto tem

projetos = ["platform.ui", "couchbase-java-client", "couchbase-jvm-core", "jgit", "egit", "spymemcached"]

for projeto in projetos:
    blocos = []
    caminho = "type_clones/" + projeto + ".csv"
    with open(caminho, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        cabeçalho = next(leitor_csv)
        for linha in leitor_csv:
            if linha[2] not in blocos and linha[3] == "0" and linha[4] == "0":
                blocos.append(linha[2])
    print(len(blocos))