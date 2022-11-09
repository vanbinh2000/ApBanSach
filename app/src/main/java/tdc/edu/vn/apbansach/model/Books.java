package tdc.edu.vn.apbansach.model;

public class Books {
    private String BookName, BookPrice,  BookInfo;
    String categories_id;

    public String getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(String categories_id) {
        this.categories_id = categories_id;
    }

    int ImageBook;
    public Books() {
    }

    public Books(String bookName, String bookPrice, int imageBook, String bookInfo) {
        BookName = bookName;
        BookPrice = bookPrice;
        ImageBook = imageBook;
        BookInfo = bookInfo;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public int getImageBook() {
        return ImageBook;
    }

    public void setImageBook(int imageBook) {
        ImageBook = imageBook;
    }

    public String getBookInfo() {
        return BookInfo;
    }

    public void setBookInfo(String bookInfo) {
        BookInfo = bookInfo;
    }
}
