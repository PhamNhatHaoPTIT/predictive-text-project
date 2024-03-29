package dao.impl;

import dao.WordDao;
import model.BloomFilter;
import model.Trie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDaoImpl implements WordDao {

    public WordDaoImpl() {

    }

    public String[] removeSymbolText(String content) {
        content = content.trim().replace(".", " ")
                .replace(",", " ")
                .replace("?", " ")
                .replace("!", " ")
                .replace(":", " ")
                .replace("&", " ")
                .replace("\n", " ")
                .replace("\t", " ")
                .replace("'", "")
                .replace("\\s+", " ")
                .replace("(", " ")
                .replace(")", " ")
                .replace("/", " ")
                .replace("+", " ")
                .replace("=", " ")
                .replace("_", " ")
                .replace("%", " ")
                .replace("^", " ")
                .replace("*", " ")
                .replace("|", " ")
                .replace("[", " ")
                .replace("]", " ")
                .replace("{", " ")
                .replace("}", " ")
                .replace(">", " ")
                .replace("<", " ")
                .replace(" - ", " ")
                .replace("\"", " ")
                .replace("@", " ")
                .replace("#", " ")
                .replace("-", " ")
                .replace(";", " ");

        String[] words = content.split("\\s+");
        return words;
    }

    @Override
    public void insertWords(String filePath, Trie trie, BloomFilter bloomFilter) throws Exception {
        String content;
        File folder = new File(filePath);
        File[] listFiles = folder.listFiles();           // get list file in folder

        for(int i = 0; i < listFiles.length; i++) {
            String fileName = listFiles[i].getName();
            if(fileName.endsWith(".xml")) {             // only xml file
                content = "";
                FileReader fileReader = new FileReader(listFiles[i]);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content += line;
                }
                Pattern pattern = Pattern.compile("<post>(.*?)</post>");
                Matcher matcher = pattern.matcher(content);
                while (matcher.find()) {
                    String temp = matcher.group(1);
                    String[] words = removeSymbolText(temp);
                    for(int j = 0; j < words.length; j++) {
                        trie.insert(words[j].toLowerCase());
                        bloomFilter.insert(words[j].toLowerCase());
                    }
                }
                fileReader.close();
                bufferedReader.close();
            }

        }
    }
}
