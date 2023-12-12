import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category implements IEntity, Serializable {
    private int id;
    private String name;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = inputId(Library.listCategory, scanner);
        this.name = inputCategoryName(Library.listCategory, scanner);
        this.status = inputStatus(scanner);
    }

    public int inputId(List<Category> categoryList, Scanner scanner) {
        System.out.println("Mời bạn nhập mã thể loại:");
        do {
            int categoryId = validate(scanner, 0);
            boolean isDuplication = false;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryId == categoryList.get(i).getId()) {
                    isDuplication = true;
                    break;
                }
            }
            if (!isDuplication) {
                return categoryId;
            } else {
                System.err.println("Mã thể loại bị trùng, vui lòng nhập lại!");
            }
        } while (true);
    }
    public boolean inputStatus(Scanner scanner){
        System.out.println("Mời bạn nhập trạng thái: ");
        do {
            String statusCategory = scanner.nextLine();
            if (statusCategory.equals("true") || statusCategory.equals("false")){
                return Boolean.parseBoolean(statusCategory);
            }
        }while (true);
    }

    public String inputCategoryName(List<Category> categoryList, Scanner scanner) {
        System.out.println("Mời bạn nhập tên thể loại: ");
        do {
            String cateBookName = lenghthString(6, 30, scanner);
            boolean isDuplication = false;
            for (int i = 0; i < categoryList.size(); i++) {
                if (cateBookName.equals(categoryList.get(i).getName())) {
                    isDuplication = true;
                    break;
                }
            }
            if (!isDuplication){
                return cateBookName;
            }else {
                System.err.println("Tên bị trùng, vui lòng nhập lại!");
            }
        } while (true);
    }

    public int validate(Scanner scanner, int i) {
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number > i) {
                    return number;
                } else {
                    System.err.println("Só nguyên lớn hơn 0, vui lòng nhập lại");
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
        System.out.println("Mã thể loại: " + this.id + " - Tên thể loại: " + this.name + " - Trạng thái: " + (this.status == true ? "Hoạt động" : "Không hoạt động"));
    }

    public static void writeDataToFile(List<Category> categoryList) {
        File file = new File("categories.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(categoryList);
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

    public static List<Category> readDataFromFile() {
        List<Category> listCategoryRead = null;
        File file = new File("categories.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listCategoryRead = (List<Category>) ois.readObject();
            return listCategoryRead;
        } catch (FileNotFoundException e) {
            listCategoryRead = new ArrayList<>();
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
        return listCategoryRead;
    }

}
