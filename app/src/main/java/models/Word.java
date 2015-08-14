package models;

import java.util.List;

public class Word
{
    private String id;
    private String word;
    private String pronunciation;

    List<Word> synonyms; // Sopomenke- ids
    List<Word> antonyms; // Protipomenke- ids


    public Word(String id, String word, String pronunciation, List<Word> synonyms, List<Word> antonyms)
    {
        this.id = id;
        this.word = word;
        this.pronunciation = pronunciation;

        if(synonyms!=null)
        {
            this.synonyms = synonyms;
        }
        if(antonyms!=null)
        {
            this.antonyms = null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public List<Word> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Word> synonyms) {
        this.synonyms = synonyms;
    }


    public List<Word> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Word> antonyms) {
        this.antonyms = antonyms;
    }
}
