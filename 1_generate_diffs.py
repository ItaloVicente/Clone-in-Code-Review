import csv
import os
import subprocess
import shutil
import configparser
config = configparser.ConfigParser()
config.read("settings.ini")
PROJECT = config['DETAILS']['project']
caminho_do_arquivo = 'metadata/'+ PROJECT +'.csv'

if os.path.exists("diffs") == False:
    os.mkdir("diffs")
if os.path.exists("diffs/" + PROJECT) == False:
    os.mkdir("diffs/" + PROJECT)

with open(caminho_do_arquivo, newline='') as arquivo_csv:
    leitor_csv = csv.reader(arquivo_csv)
    # Para pular o cabeçalho, se houver um
    cabeçalho = next(leitor_csv)
    count = 0
    for linha in leitor_csv:
        if linha[0]!="Review Unable to Download " and linha[0] != "Not Found review: " and len(linha)>10:
            print("Generate diff of review: " + linha[1] + " rev: " + linha[2])
            if linha[9] != linha[10]:
                commit_id1 = linha[9]
                commit_id2 = linha[10]
                file_name = linha[1] + "_" + linha[2]
                repo_path = "git_repos/" + PROJECT  # Atualize com o caminho para o diretório do repositório

                # Caminho onde o arquivo diff será salvo temporariamente
                diff_file_path = f"{file_name}.diff"

                # Comando git show para obter o diff do commit especificado para o arquivo
                command = f"git -C {repo_path} diff {commit_id1}..{commit_id2} > {diff_file_path}"

                # Executa o comando git show no contexto do diretório do repositório
                process = subprocess.Popen(command, shell=True)
                process.wait()  # Aguarda o término do processo para garantir que o arquivo seja criado

                # Caminho para onde o arquivo diff será movido
                destination_path = "diffs/" + PROJECT

                # Move o arquivo para o novo destino, caso Windows, Linux precisa de subprocess (mv)
                shutil.move(diff_file_path, f"{destination_path}/{diff_file_path}")
            else:
                print("Revision no after: " + linha[1])
                count+=1
    print("Revision no after: " + count)

