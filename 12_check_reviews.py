import configparser
import csv
import json
import os
import hashlib
import shutil
from collections import defaultdict

def analise_revisions(caminho_type_clones):
    with open(caminho_type_clones, newline='') as arquivo_csv:
        leitor_csv = csv.reader(arquivo_csv)
        next(leitor_csv)  # Ignorar o cabeçalho

        revisions = {}
        for idx, linha in enumerate(leitor_csv, start=2):
            # Usa o índice (linha do CSV) como chave e armazena a linha completa
            if linha[3] == "0" and linha[4] == "0":
                revisions[idx] = linha

    return revisions

def hash_file(file_path):
    """Gera um hash SHA-256 para o conteúdo do arquivo."""
    hash_sha256 = hashlib.sha256()
    with open(file_path, 'rb') as f:
        for chunk in iter(lambda: f.read(4096), b""):
            hash_sha256.update(chunk)
    return hash_sha256.hexdigest()

def verificar_blocos_com_hash(dict_revisions_blocks, project):
    hash_map = {}  # Mapeia hash para a linha do bloco
    blocos_unicos = {}

    for idx, linha in dict_revisions_blocks.items():
        review = linha[0]
        revision = linha[1]
        block_path = linha[2]  # Caminho do bloco
        path_block = f"blocks/{project}/{review}_{revision}/{block_path}"

        if os.path.exists(path_block):
            block_hash = hash_file(path_block)

            if block_hash not in hash_map:
                # Adicionar o hash à tabela hash e armazenar a linha correspondente
                hash_map[block_hash] = [linha]
                blocos_unicos[idx] = linha
            else:
                L = hash_map[block_hash]
                L.append(linha)
                hash_map[block_hash] = L
    return blocos_unicos
def salvar_json(blocos_unicos, project):
    """Salva os blocos únicos em um arquivo JSON."""
    caminho_saida = f"type_clones/{project}_equals_reviews.json"
    os.makedirs(os.path.dirname(caminho_saida), exist_ok=True)

    with open(caminho_saida, 'w', encoding='utf-8') as arquivo_json:
        # Salvar o dicionário diretamente como JSON
        json.dump(blocos_unicos, arquivo_json, ensure_ascii=False, indent=4)


config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_type_clones = "type_clones/" + PROJECT + "(original).csv"
revisions = analise_revisions(caminho_type_clones)
blocos_unicos = verificar_blocos_com_hash(revisions, PROJECT)
salvar_json(blocos_unicos,PROJECT)
print(len(blocos_unicos))

"""
platform.ui - 5374
jgit - 1106
egit - 1127
couchbase-jvm-core - 190
couchbase-java-client - 342
spymemcached - 352
"""