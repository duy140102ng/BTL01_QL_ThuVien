
import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static List<Category> listCategory;
    public static List<Book> listBook;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        listCategory = Category.readDataFromFile();
        listBook = Book.readDataFromFile();
        int choice = 0;
        do {
            System.out.println(CYAN + BLUE + "=====" + RED + "QUẢN LÝ THƯ VIỆN" + CYAN + "=====" + RESET);
            System.out.println("1. " + YELLOW + "Quản lý thể loại" + RESET);
            System.out.println("2. " + YELLOW + "Quản lý sách" + RESET);
            System.out.println("3. " + YELLOW + "Thoát.");
            System.out.println(RED +"Lựa chọn của bạn: " +RESET);
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
                    System.out.println(YELLOW +  "⚠" + RED+ " Mời bạn lựa chọn từ 1-3!");
            }
        } while (true);
    }

    public static void displayMenuCategory() {

        boolean isExit = true;
        int choice = 0;
        do {
            System.out.println(CYAN + BLUE + "===== " + RED + "QUẢN LÝ THỂ LOẠI" + CYAN + " =====" + RESET);
            System.out.println("1. " + YELLOW + "Thêm mới thể loại" + RESET);
            System.out.println("2. " + YELLOW + "Hiển thị danh sách theo tên A – Z" + RESET);
            System.out.println("3. " + YELLOW + "Thống kê thể loại và số sách có trong mỗi thể loại" + RESET);
            System.out.println("4. " + YELLOW + "Cập nhật thể loại" + RESET);
            System.out.println("5. " + YELLOW + "Xóa thể loại" + RESET);
            System.out.println("6. " + YELLOW + "Quay lại" + RESET);
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
                    System.out.println(YELLOW +  "⚠" + RED+ " Mời bạn lựa chọn từ 1-6!");
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
        System.out.printf("%s                         ꧁༺THÔNG TIN THỂ LOẠI༻꧂\n", RED);
        System.out.println("╔═━─────────────────────────────────━▒ ۞ ▒━──────────────────────────────────━═╗");
        System.out.printf("%s|%-21s |%-31s |%-22s|%s%n", YELLOW, "Mã thể loại", "Tên thể loại", "Trạng thái", PURPLE);
        System.out.println("╔═━──────────────────────────────────────────────────────────────────────────━═╗");
        try {
            List<Category> sortedCategory = listCategory.stream()
                    .sorted(Comparator.comparing(Category::getName))
                    .collect(Collectors.toList());

            for (Category category : sortedCategory) {
                category.output();
                System.out.printf("%s╔═━──────────────────────────────────────────────────────────────────────────━═╗\n", PURPLE);
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void printStatistics() {
        System.out.printf("%s THỐNG KÊ THỂ LOẠI VÀ SỐ SÁCH TRONG MỖI THỂ LOẠI:\n", RED);
        System.out.println("୨♡୧┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈୨♡୧");
        System.out.printf("%s|%-16s |%-21s |%-12s|%s%n", YELLOW, "Mã thể loại", "Tên thể loại", "Số sách", CYAN);
        System.out.println("୨♡୧┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈୨♡୧");
        try {
            for (Category category : listCategory) {
                int categoryId = category.getId();
                String categoryName = category.getName();
                long bookCount = listBook.stream().filter(book -> book.getCategoryId() == categoryId).count();

                System.out.printf("| %s%-15d%s | %-20s | %s%-10d%s |%n", CYAN, categoryId, CYAN, categoryName, CYAN, bookCount, CYAN);
                System.out.println("୨♡୧┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈୨♡୧");
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
            boolean isReferenced = listBook.stream().anyMatch(book -> book.getId().equals(deleteId));
            if (isReferenced) {
                System.err.println("Thể loại đang có sách, không thể xóa thể loại");
            } else {
                listCategory.removeIf(category -> category.getId() == deleteId);
                System.out.println("Thể loại đã xóa thành công");
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
            System.out.println(PURPLE + BLUE + "===== " + RED + "QUẢN LÝ SÁCH" + PURPLE + " =====" + RESET);
            System.out.println("1. " + YELLOW + "Thêm mới sách" + RESET);
            System.out.println("2. " + YELLOW + "Cập nhật thông tin sách" + RESET);
            System.out.println("3. " + YELLOW + "Xóa sách" + RESET);
            System.out.println("4. " + YELLOW + "Tìm kiếm sách" + RESET);
            System.out.println("5. " + YELLOW + "Hiển thị danh sách sách theo nhóm thể loại" + RESET);
            System.out.println("6. " + YELLOW + "Quay lại" + RESET);
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
                    break;
                default:
                    System.out.println(YELLOW +  "⚠" + RED+ " Mời bạn lựa chọn từ 1-6!");
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
            String updateId = scanner.nextLine().trim();
            Optional<Book> updateBook = listBook.stream()
                    .filter(book -> {
                        String bookId = book.getId();
                        return bookId != null && bookId.equals(updateId);
                    })
                    .findFirst();
            if (updateBook.isPresent()) {
                System.out.println("Mời bạn nhập thông tin mới cho sách");
                int index = listBook.indexOf(updateBook.get());
                Book updatedBook = new Book();
                updatedBook.input(scanner);
                updatedBook.setId(updateId);
                listBook.set(index, updatedBook);
                System.out.println("Thông tin đã được cập nhật thành công");
            } else {
                System.err.println("Không tìm thấy sách cần cập nhật");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static void deleteBook() {
        System.out.println("Mời bạn nhập mã sách cần xóa");
        try {
            String deleteId = scanner.nextLine().trim();
            boolean isBookFound = listBook.stream()
                    .anyMatch(book -> {
                        String bookId = book.getId();
                        return bookId != null && bookId.equals(deleteId);
                    });
            if (isBookFound) {
                listBook.removeIf(book -> {
                    String bookId = book.getId();
                    return bookId != null && bookId.equals(deleteId);
                });
                System.out.println("Thông tin sách đã xóa thành công");
            } else {
                System.out.println("Không tìm thấy sách với mã đã nhập");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    public static List<Book> searchBooks(String keyBook) {
        String keyLowerCase = keyBook.toLowerCase();
        return listBook.stream()
                .filter(book -> containsIgnoreCase(book.getTitle(), keyLowerCase) ||
                        containsIgnoreCase(book.getAuthor(), keyLowerCase) ||
                        containsIgnoreCase(book.getPublisher(), keyLowerCase))
                .collect(Collectors.toList());
    }

    public static void displayBooks(List<Book> books) {
        if (books == null) {
            System.out.println("Không tìm thấy sách phù hợp");
        } else {
            System.out.println("DANH SÁCH SÁCH:");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("|%s%-10s |%-20s |%-20s |%-20s |%-20s |%-20s |%-15s |%s%n",
                    YELLOW, "Mã sách", "Tiêu đề sách", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Mô tả sách", "Mã thể loại", RESET);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

            books.forEach(book -> {
                System.out.printf("|%-10s |%-20s |%-20s |%-20s |%-20s |%-20s |%-15s |%n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                        book.getYear(), book.getDescription(), book.getCategoryId());
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            });
        }
    }

    public static void searchBook() {
        System.out.println("Mời bạn nhập ký tự để tìm kiếm: ");
        try {
            String keyBook = scanner.nextLine().trim();

            if (!keyBook.isEmpty()) {
                List<Book> searchResults = searchBooks(keyBook);
                if (!searchResults.isEmpty()) {
                    displayBooks(searchResults);
                } else {
                    System.out.println("Không tìm thấy sách phù hợp với từ khóa \"" + keyBook + "\"");
                }
            } else {
                System.err.println("Vui lòng nhập từ khóa tìm kiếm");
            }
        } catch (Exception ex) {
            System.err.println("Có lỗi: " + ex);
        }
    }

    private static boolean containsIgnoreCase(String source, String key) {
        return source.toLowerCase().contains(key);
    }

    public static void displayListBook() {
        System.out.println("DANH SÁCH SÁCH THEO NHÓM THỂ LOẠI: ");
        System.out.printf("%s+----------------------+----------------------+%n", YELLOW);
        Map<Integer, List<Book>> booksByCategory = listBook.stream().collect(Collectors.groupingBy(Book::getCategoryId));
        System.out.printf("%s|%-21s |%-21s |%n", YELLOW, "Thể loại", "Tên sách");
        System.out.println("+----------------------+----------------------+");
        booksByCategory.forEach((category, books) -> {
            System.out.printf("%s|%-21s |%-21s |%n", BLUE, getCategoryNameById(category), "");
            books.forEach(book -> {
                System.out.printf("|%-28s |%s%-21s |%n", BLUE, "", book.getTitle());
            });
            System.out.println("+----------------------+----------------------+");
        });
    }

    public static String getCategoryNameById(int categoryId) {
        for (Category category : listCategory) {
            if (category.getId() == categoryId) {
                return category.getName();
            }
        }
        return "";
    }
}