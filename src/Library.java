
import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    public static List<Category> listCategory;
    public static List<Book> listBook;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        listCategory = Category.readDataFromFile();
        listBook = Book.readDataFromFile();
        int choice = 0;
        do {
            System.out.println("===== QUẢN LÝ THƯ VIỆN =====\n" +
                    "1. Quản lý Thể loại\n" +
                    "2. Quản lý Sách\n" +
                    "3. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Bạn hãy nhập số thứ tự!");
            }
            switch (choice) {
                case 1:
                    displayMenuCategory();
                    break;
                case 2:
                    displayMenuBook();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Mời bạn lựa chọn từ 1-3!");
            }
        } while (true);
    }

    public static void displayMenuCategory() {

        boolean isExit = true;
        int choice = 0;
        do {
            System.out.println("===== QUẢN LÝ THỂ LOẠI =====\n" +
                    "1. Thêm mới thể loại\n" +
                    "2. Hiển thị danh sách theo tên A – Z\n" +
                    "3. Thống kê thể loại và số sách có trong mỗi thể loại\n" +
                    "4. Cập nhật thể loại\n" +
                    "5. Xóa thể loại\n" +
                    "6. Quay lại");
            System.out.println("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.err.println("Mời bạn nhập là số thứ tự");
            }
            switch (choice) {
                case 1:
                    addCategory();
                    Category.writeDataToFile(Library.listCategory);
                    break;
                case 2:
                    outputCategory();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 4:
                    updateCategory();
                    Category.writeDataToFile(Library.listCategory);
                    break;
                case 5:
                    deleteCategory();
                    Category.writeDataToFile(Library.listCategory);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.out.println("Mời bạn nhập từ 1-6!");
            }
        } while (isExit);
    }

    public static void addCategory() {
        System.out.println("Mời bạn nhập số lượng thế loại cần thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Category category = new Category();
                category.input(scanner);
                listCategory.add(category);
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void outputCategory() {
        System.out.println("THÔNG TIN THỂ LOẠI:");
        try {
            List<Category> sortedCategory = listCategory.stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
            for (Category category : sortedCategory) {
                category.ouput();
                System.out.println("---------------------------------------------");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void printStatistics() {
        System.out.println("THỐNG KÊ THỂ LOẠI VÀ SỐ SÁCH TRONG MỖI THỂ LOẠI: ");
        try {
            for (Category category : listCategory) {
                int categoryId = category.getId();
                String categoryName = category.getName();
                long bookCount = listBook.stream().filter(book -> book.getCategoryId() == categoryId).count();
                System.out.println("Mã thể loại: " + categoryId + " - Tên thể loại: " + categoryName +
                        "\nSố sách: " + bookCount);
                System.out.println("-------------------------------------------");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void updateCategory() {
        System.out.println("Mời bạn nhập mã thể loại cần cập nhật: ");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());
            Optional<Category> updateCategory = listCategory.stream().filter(category -> category.getId() == updateId).findFirst();
            updateCategory.ifPresent(category -> {
                System.out.println("Mời bạn nhập nhập thông tin mới cho thể loại");
                category.input(scanner);
                System.out.println("Thể loại đã được cập nhật thành công");
            });
            if (!updateCategory.isPresent()) {
                System.err.println("Không tìm thấy thể loại cần cập nhật");
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Mã thể loại phải là số nguyên");
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void deleteCategory() {
        System.out.println("Mời bạn nhập mã thể loại cần xóa");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine());
            boolean isReferenced = listCategory.stream().anyMatch(category -> category.getId() == deleteId);
            if (!isReferenced) {
                System.err.println("Thể loại đang có sách, không thể xóa thể loại");
            } else {
                listCategory.removeIf(category -> category.getId() == deleteId);
                System.out.println("Thể loại đa xóa thành công");
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Mã thể loại phải là số nguyên");
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void displayMenuBook() {
        boolean isExit = true;
        int choice = 0;
        do {
            System.out.println("===== QUẢN LÝ SÁCH =====\n" +
                    "1. Thêm mới sách\n" +
                    "2. Cập nhật thông tin sách\n" +
                    "3. Xóa sách\n" +
                    "4. Tìm kiếm sách\n" +
                    "5. Hiển thị danh sách sách theo nhóm thể loại\n" +
                    "6. Quay lại");
            System.out.println("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.err.println("Nhập lựa chọn là số thứ tự");
            }
            switch (choice) {
                case 1:
                    inputBook();
                    Book.writeDataToFile(Library.listBook);
                    break;
                case 2:
                    updateBook();
                    Book.writeDataToFile(Library.listBook);
                    break;
                case 3:
                    deleteBook();
                    Book.writeDataToFile(Library.listBook);
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    displayListBook();
                    break;
                case 6:
                    isExit = false;
                default:
                    System.out.println("Mời bạn nhập từ 1-6!");
            }
        } while (isExit);
    }

    public static void inputBook() {
        System.out.println("Mời bạn nhập số sách: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Book book = new Book();
                book.input(scanner);
                listBook.add(book);
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void updateBook() {
        System.out.println("Mời bạn nhập mã sách cần cập nhật: ");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());
            Optional<Book> updateBook = listBook.stream().filter(book -> book.getId().equals(updateId)).findFirst();
            updateBook.ifPresent(book -> {
                System.out.println("Mời bạn nhập nhập thông tin mới cho thông tin sách");
                book.input(scanner);
                System.out.println("Thông tin đã được cập nhật thành công");
            });
            if (!updateBook.isPresent()) {
                System.err.println("Không tìm thấy thể loại cần cập nhật");
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Mã sách phải là số nguyên");
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void deleteBook() {
        System.out.println("Mời bạn nhập mã sách cần xóa");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine());
            listBook.stream().anyMatch(book -> book.getId().equals(deleteId));
            listBook.removeIf(book -> book.getId().equals(deleteId));
            System.out.println("Thng tin sách đã xóa thành công");
        } catch (NumberFormatException nfe) {
            System.err.println("Mã sách phải là số nguyên");
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static List<Book> searchBooks(String keybook) {
        return listBook.stream().filter(book ->
                book.getTitle().toLowerCase().contains(keybook.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(keybook.toLowerCase()) ||
                        book.getPublisher().toLowerCase().contains(keybook.toLowerCase())
        ).collect(Collectors.toList());
    }

    public static void displayBooks(List<Book> listBook) {
        listBook.forEach(book -> {
            book.input(scanner);
            System.out.println("-----------------------------------------");
        });
    }

    public static void searchBook() {
        System.out.println("Mời bạn nhập ký tự để tìm kiếm: ");
        try {
            String keyBook = scanner.nextLine().trim();
            if (!keyBook.isEmpty()) {
                List<Book> searchResults = searchBooks(keyBook);
                displayBooks(searchResults);
            } else {
                System.err.println("Vui lòng nhập từ khóa tìm kiếm");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void displayListBook() {
        System.out.println("DANH SÁCH SÁCH THEO NHÓM THỂ LOẠI: ");
        Map<String, List<Book>> bookByCategory = listBook.stream().collect(Collectors.groupingBy(Book::getTitle));
        bookByCategory.forEach((category, books) -> {
            System.out.println("Thể loại " + category);
            books.forEach(book -> {
                System.out.println(" " + book.getTitle());
            });
            System.out.println("-----------------------------------------");
        });
    }

}