import javax.swing.table.*;
// Ayala Bautista Cuauhtemoc
import javax.swing.*;
import java.awt.event.*;
import  java.sql.*;

public class tarea2 {

  public static void main(String[] args) {
    JFrame ventana = new JFrame("Tarea 2");
        Icon IconoMostrar = new ImageIcon("mostartabla.jpg");
        JButton mostrar = new JButton(IconoMostrar);
        Icon IconoUpdate = new ImageIcon("iconfinder_update_984748.jpg");
        JButton boton_update = new JButton(IconoUpdate);
        Icon Insert = new ImageIcon("iconfinder_table_row_insert_64.jpg");
        JButton boton_insert = new JButton(Insert);
        Icon Delete = new ImageIcon("iconfinder_close_309090.jpg");
        JButton boton_delete = new JButton(Delete);
        DefaultTableModel modelo = new DefaultTableModel();
        JTable tabla1 = new JTable(modelo);
        JScrollPane scroll = new  JScrollPane(tabla1);



        ActionListener listener = new ActionListener(){

          @Override
          public void actionPerformed(ActionEvent e){

            if(e.getSource() == mostrar){
              try{
          Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException ex){
          System.out.println(ex);
        }

        try {
          Connection c = DriverManager.getConnection(
          "jdbc:mysql://localhost/company", "vamp", "Vampire1");
          Statement state = c.createStatement();
          ResultSet rS = state.executeQuery("select * from employees");
          ResultSetMetaData rSMD = rS.getMetaData();
          int columnas = rSMD.getColumnCount();

          for (int i = 1; i <= columnas ; i++){
             modelo.addColumn(rSMD.getColumnName(i));
          }

          while (rS.next()){
               Object[] fila = new Object[columnas];

               for (int i = 0; i < columnas; i++)
                   fila[i] = rS.getObject(i + 1);

               modelo.addRow(fila);
          }

        } catch(SQLException s){
          System.out.println(s);
          JOptionPane.showMessageDialog(null, s);
        }

      }

      if (e.getSource() == boton_delete) {
        JDialog de = new JDialog(ventana, "Delete");
        de.setVisible(true);
        de.setSize(200,200);
      }

      if (e.getSource() == boton_insert) {
        JDialog de = new JDialog(ventana, "Insert");
        de.setVisible(true);
        de.setSize(200,200);
      }

      if (e.getSource() == boton_update) {
        JDialog de = new JDialog(ventana, "Update");
        de.setVisible(true);
        de.setSize(200,200);
      }

      }
        };

        mostrar.addActionListener(listener);
        boton_delete.addActionListener(listener);
        boton_insert.addActionListener(listener);
        boton_update.addActionListener(listener);

        mostrar.setBounds(30 , 25,50, 50);
        boton_update.setBounds(85, 25, 50, 50);
        boton_insert.setBounds(140, 25 , 50 , 50);
        boton_delete.setBounds(195, 25 , 50, 50);
        scroll.setBounds(30, 100, 400, 250);
        tabla1.setBounds(30, 100, 400, 250);


       ventana.add(mostrar);
       ventana.add(boton_update);
       ventana.add(boton_delete);
       ventana.add(boton_insert);
       ventana.add(scroll);

        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null);

  }

}
