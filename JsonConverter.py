import json

filePath = "D:\\Classes\\UTPB COSC-4380 Crypto\\Project 1\\bigrams.json"
outputPath = "D:\\Classes\\UTPB COSC-4380 Crypto\\Project 1\\bigrams.txt"

bigrams = {}
with open(filePath, "r") as jsonFile:
    bigram_counts = json.load(jsonFile)
    total_count = 0
    for bigram_count in bigram_counts:
        total_count += bigram_count[1]
    for bigram_count in bigram_counts:
        bigrams[bigram_count[0]] = bigram_count[1] / total_count
    #print(bigrams)
    with open(outputPath, "w") as textFile:
        for bigram in bigrams:
            #print(f"{bigram} {bigrams[bigram]}")
            textFile.write(f"{bigram} {bigrams[bigram]}\n")

