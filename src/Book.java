import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book implements IEntity, Serializable {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String description;
    private int categoryId;

    public Book() {
    }

    public Book(String id, String title, String author, String publisher, int year, String description, int categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = inputId(Library.listBook, scanner);
        this.title = inputTitleBook(Library.listBook, scanner);
        this.author = inputAuthor(scanner);
        this.publisher = inputPublisher(scanner);
        this.year = inputYear(scanner);
        this.description = inputDescription(scanner);
        this.categoryId = inputCategoryId(scanner, Library.listCategory);
    }

    public String inputId(List<Book> bookList, Scanner scanner) {
        System.out.println("Mời bạn nhập mã sách: ");
        do {
            this.id = lenghthString(3, 4, scanner);
            if (this.id.charAt(0) == 'B') {
                boolean isDupcription = false;
                for (int i = 0; i < bookList.size(); i++) {
                    if (this.id.equals(bookList.get(i).getId())) {
                        isDupcription = true;
                        break;
                    }
                }
                if (!isDupcription) {
                    return this.id;
                } else {
                    System.err.println("Mã sách bị tùng");
                }
            } else {
                System.err.println("Ký tự đầu tiên của sách là B, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputTitleBook(List<Book> bookList, Scanner scanner) {
        System.out.println("Mời bạn nhập tiêu đề sách: ");
        do {
            this.title = lenghthString(6, 50, scanner);
            boolean isDupcription = false;
            for (int i = 0; i < bookList.size(); i++) {
                if (this.title.equals(bookList.get(i).getTitle())) {
                    isDupcription = true;
                    break;
                }
            }
            if (!isDupcription) {
                return this.title;
            } else {
                System.err.println("Tiêu đề đã có, vui lòng nhập lại!");
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {

        do {
            System.out.println("Mời bạn nhập tên tác giả: ");
            String authorBook = scanner.nextLine();

            if (authorBook.trim().isEmpty()) {
                System.err.println("Tên tác giả không được bỏ trống, vui lòng nhập lại!");
            }else {
                return authorBook;
            }
        } while (true);
    }

    public String inputPublisher(Scanner scanner) {
        System.out.println("Mời bạn nhập nhà xuất bản: ");
        do {
            String publisher = scanner.nextLine().trim();
            if (publisher.isEmpty()) {
                System.err.println("Nhà xuất bản không được bỏ trống, vui lòng nhập lại!");
            }else {
                return publisher;
            }
        } while (true);
    }

    public int inputYear(Scanner scanner) {
        int yearNow = LocalDate.now().getYear();
        System.out.println("Mời bạn nhập năm xuất bản: ");
        do {
            this.year = validate(scanner);
            if (this.year <= 1970 || this.year > yearNow) {
                System.err.println("Năm xuất bản không hợp lệ, vui lòng nhập lại!");
            }else {
                return this.year;
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println("Mời bạn nhập mô tả sách: ");
        do {
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.err.println("Mô tả sách không được bỏ trống, vui lòng nhập lại!");
            }else {
                return description;
            }
        } while (true);
    }

    public int inputCategoryId(Scanner scanner, List<Category> categoryList) {
        System.out.println("Chọn danh mục của sản phẩm: ");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, categoryList.get(i).getName());
        }
        System.out.println("Lựa chọn của bạn: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > categoryList.size()) {
            System.err.println("Lựa chọn không hợp lệ, vui lòng chọn lại");
        }
        return categoryList.get(choice - 1).getId();
    }

    public int validate(Scanner scanner) {
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.err.println("Số nguyên lớn hơn 0, vui lòng nhập lại");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng nhập số nguyên");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (true);
    }

    public String lenghthString(int a, int b, Scanner scanner) {
        do {
            try {
                String str = scanner.nextLine();
                if (str.length() > a && str.length() <= b) {
                    return str;
                } else {
                    System.err.println("Nhập đúng số lượng ký tự!");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    @Override
    public void ouput() {
        System.out.println("Mã sách: " +this.id + " - Tiêu đề sách: " + this.title + " - Tên tắc giả: \n" + this.author+
                "Nhà xuất bản: " + this.publisher + " - Năm xuất bản: " + this.year + "- Mô tả sách: " + (this.description + " - Mã thể loại sách: " + this.categoryId));
    }

    public static void writeDataToFile(List<Book> bookList) {
        File file = new File("books.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bookList);
            oos.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Book> readDataFromFile() {
        List<Book> listBookRead = null;
        File file = new File("books.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listBookRead = (List<Book>) ois.readObject();
            return listBookRead;
        } catch (FileNotFoundException e) {
            listBookRead = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listBookRead;
    }
}
