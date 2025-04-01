import csv
import os
import matplotlib.pyplot as plt
from matplotlib_venn import venn3


def count_intersection(caminho_type_clones):
    counts = {
        "move": 0, "move_and_test": 0, "move_and_diff_and_test": 0,
        "move_and_diff": 0, "test_and_diff": 0, "nenhum_dos_3": 0,
        "test": 0, "diff": 0
    }
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        next(leitor_csv)  # Pula o cabeçalho
        antecessor = None
        index_move = index_test = index_diff = 0

        for linha in leitor_csv:
            atual = f"{linha[0]}_{linha[1]}_{linha[2]}"
            if antecessor is None:
                antecessor = atual

            if atual != antecessor:
                key = (index_move, index_test, index_diff)
                counts = update_counts(counts, key)
                index_move = index_test = index_diff = 0
                antecessor = atual

            if int(linha[3]) == 1:
                index_move = 1
            if int(linha[4]) == 1:
                index_test = 1
            if int(linha[5]) == 1:
                index_diff = 1

        if antecessor is not None:
            key = (index_move, index_test, index_diff)
            counts = update_counts(counts, key)

    return counts


def update_counts(counts, key):
    if key == (0, 0, 0):
        counts["nenhum_dos_3"] += 1
    elif key == (0, 0, 1):
        counts["diff"] += 1
    elif key == (0, 1, 0):
        counts["test"] += 1
    elif key == (0, 1, 1):
        counts["test_and_diff"] += 1
    elif key == (1, 0, 0):
        counts["move"] += 1
    elif key == (1, 0, 1):
        counts["move_and_diff"] += 1
    elif key == (1, 1, 0):
        counts["move_and_test"] += 1
    elif key == (1, 1, 1):
        counts["move_and_diff_and_test"] += 1
    return counts


def plot_venn(counts, projects):
    plt.figure(figsize=(6, 6))


    equal_size = 100  
    subsets = (
        equal_size, equal_size, equal_size,
        equal_size, equal_size, equal_size,
        equal_size
    )

    venn = venn3(subsets=subsets, set_labels=('Moved', 'Test', 'Diff'))

    venn.get_label_by_id('100').set_text(counts["move"])
    venn.get_label_by_id('010').set_text(counts["test"])
    venn.get_label_by_id('110').set_text(counts["move_and_test"])
    venn.get_label_by_id('001').set_text(counts["diff"])
    venn.get_label_by_id('101').set_text(counts["move_and_diff"])
    venn.get_label_by_id('011').set_text(counts["test_and_diff"])
    venn.get_label_by_id('111').set_text(counts["move_and_diff_and_test"])

    plt.title(f"Projects: {', '.join(projects)}", fontsize=8, pad=20)

    plt.text(0.5, -0.12, f'None of the three: {counts["nenhum_dos_3"]}',
             color='red', fontsize=12, ha='center', transform=plt.gca().transAxes)

    plt.subplots_adjust(top=0.95, bottom=0.2)

    plt.tight_layout()
    plt.savefig(f"venn_diagram_multiple_projects.png")
    plt.show()


PROJECTS = ["platform.ui", "egit", "jgit", "couchbase-jvm-core", "couchbase-java-client", "spymemcached"]
counts_total = {"move": 0, "move_and_test": 0, "move_and_diff_and_test": 0,
                "move_and_diff": 0, "test_and_diff": 0, "nenhum_dos_3": 0,
                "test": 0, "diff": 0}

for PROJECT in PROJECTS:
    caminho_type_clones = f"type_clones/{PROJECT}.csv"
    if os.path.exists(caminho_type_clones):
        counts = count_intersection(caminho_type_clones)
        for key in counts_total:
            counts_total[key] += counts[key]

plot_venn(counts_total, PROJECTS)

