package emergingtech.lyit.ie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class DataLoadWorker extends SwingWorker<TableModel, AbstractTableModel> {

    private final JTable table;

    public DataLoadWorker(JTable table) {
        this.table = table;
    }

    @Override
    protected AbstractTableModel doInBackground() throws Exception {
        Vector data = new Vector();
        Vector columns = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String stmt = "SELECT * FROM customers ORDER BY customer_id asc";
            ps = DBConnection.getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            //store column names  
            for (int i = 1; i <= columnCount; i++) {
                columns.add(md.getColumnName(i));
            }

            columns.ensureCapacity(columnCount);

            Vector row;
            row=new Vector(columnCount);
      
            while (rs.next()) {

                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);

                //Debugging                
            }

            // List.setModel(tableModel);

        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
        }

        AbstractTableModel tableModel = new DefaultTableModel(data, columns);
        return tableModel;
    }
    
    
    @Override
    protected void done() {
        try {
            TableModel model = get();
            table.setModel(model);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
