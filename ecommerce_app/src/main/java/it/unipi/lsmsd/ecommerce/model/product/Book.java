package it.unipi.lsmsd.ecommerce.model.product;

public class Book extends Product{

    private String summary;
    private String language;
    private Integer numberPages;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(Integer numberPages) {
        this.numberPages = numberPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "summary='" + summary + '\'' +
                ", language='" + language + '\'' +
                ", numberPages=" + numberPages +
                '}';
    }
}
