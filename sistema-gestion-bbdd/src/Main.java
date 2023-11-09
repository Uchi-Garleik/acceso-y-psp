import dao.ExportDAO;
import dao.ImportDAO;

public class Main {
    public static void main(String[] args) {
//        ExportDAO exportDAO = new ExportDAO();
//        exportDAO.exportTableToXML("usuarios");

         ImportDAO importDAO = new ImportDAO();
         importDAO.importTableToSQL();



    }
}