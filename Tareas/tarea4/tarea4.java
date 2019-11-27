import javax.swing.table.*;
// Ayala Bautista Cuauhtemoc
import javax.swing.*;
import java.awt.event.*;
import  java.sql.*;

public class tarea4 {

    public static void QueryBuscador(DefaultTableModel modelo , String query){
        try {
            Conexion2 conn = new Conexion2();
            Connection c = conn.miconexion("localhost", "company", "vamp", "Vampire1");
            Statement state = c.createStatement();
            ResultSet rS = state.executeQuery("SELECT * FROM employees WHERE name LIKE \"" + query +"%\" ;");
            ResultSetMetaData rSMD = rS.getMetaData();
            int columnas = rSMD.getColumnCount();
            modelo.setRowCount(0);
            modelo.setColumnCount(0);
            for (int i = 1; i <= columnas ; i++){
                modelo.addColumn(rSMD.getColumnName(i));
            }
            while (rS.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++)
                    fila[i] = rS.getObject(i + 1);

                modelo.addRow(fila);
            }
            c.close();
        } catch(SQLException s){
            System.out.println(s);
            JOptionPane.showMessageDialog(null, s);
        }
    }

    public static void CargarTabla(DefaultTableModel modelo ) {
        try {
            Conexion2 conn = new Conexion2();
            Connection c = conn.miconexion("localhost", "company", "vamp", "Vampire1");
            Statement state = c.createStatement();
            ResultSet rS = state.executeQuery("select * from employees");
            ResultSetMetaData rSMD = rS.getMetaData();
            int columnas = rSMD.getColumnCount();
            modelo.setRowCount(0);
            modelo.setColumnCount(0);
            for (int i = 1; i <= columnas ; i++){
                modelo.addColumn(rSMD.getColumnName(i));
            }
            while (rS.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++)
                    fila[i] = rS.getObject(i + 1);

                modelo.addRow(fila);
            }
            c.close();
        } catch(SQLException s){
            System.out.println(s);
            JOptionPane.showMessageDialog(null, s);
        }
    }

    public static void YeetEmployee(String id){
        try {
            Conexion2 conn = new Conexion2();
            Connection c = conn.miconexion("localhost", "company", "vamp", "Vampire1");
            Statement state = c.createStatement();
            state.executeUpdate("delete from employees where employeid =" + id);
            c.close();
        } catch(SQLException s){
            System.out.println(s);
            JOptionPane.showMessageDialog(null, s);
        }
    }

    public static void Insertar(String ...valores)  {
        String unidos = "";
        for (String i: valores ) {
            unidos = unidos + "'"+ i + "',";
        }
        unidos = unidos.substring(0, unidos.length() - 1);
        System.out.println(unidos);
        unidos = "INSERT INTO employees VALUES("+ unidos + ");";

        try {
            Conexion2 conn = new Conexion2();
            Connection c = conn.miconexion("localhost", "company", "vamp", "Vampire1");
            Statement state = c.createStatement();
            state.executeUpdate(unidos);

        } catch(SQLException s){
            System.out.println(s);
            JOptionPane.showMessageDialog(null, s);
        }
    }

    public static void Actualizar(String id,  String nombre, String puesto , String salario){
        String glued = "UPDATE employees SET name =" + nombre + ", jobtitle = "+ puesto
                + ", salary = " + salario + " WHERE employeid = " + id;
        try {
            Conexion2 conn = new Conexion2();
            Connection c = conn.miconexion("localhost", "company", "vamp", "Vampire1");
            Statement state = c.createStatement();
            state.executeUpdate(glued);
            c.close();
        } catch(SQLException s){
            System.out.println(s);
            JOptionPane.showMessageDialog(null, s);
        }
    }

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
        JLabel buscar = new JLabel("Buscar:");
        JTextField buscador = new JTextField();

        ActionListener Botoneslistener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                if(e.getSource() == mostrar){
                    CargarTabla(modelo);
                }

                if (e.getSource() == boton_delete) {
                    if (tabla1.getSelectedRow() != -1) {
                        int confirmar = JOptionPane.showConfirmDialog(null, "Estas seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (confirmar == 0) {
                            Object id = "";
                            int linea_id = tabla1.getSelectedRow();
                            id = tabla1.getValueAt(linea_id, 0);
                            YeetEmployee(id.toString());// metodo para borrar empleado
                            CargarTabla(modelo);
                        }
                    } else{
                        JOptionPane.showMessageDialog(null, "No hay dato seleccionado :)");
                    }


                }

                if (e.getSource() == boton_insert) {
                    JDialog de = new JDialog(ventana, "Insert");
                    de.setLayout(null);

                    JLabel IDtag = new JLabel("EmployeeID:");
                    JTextField EmployeeID = new JTextField();
                    JLabel NombreTag = new JLabel("full name:");
                    JTextField name = new JTextField();
                    JLabel PuestoTag = new JLabel("Puesto:");
                    JTextField puesto = new JTextField();
                    JLabel SalarioTag = new JLabel("Salario");
                    JTextField salario = new JTextField();
                    JButton insertar = new JButton("Insertar");

                    IDtag.setBounds(20,20,90, 25);
                    EmployeeID.setBounds(110, 20, 40, 25);
                    NombreTag.setBounds(20, 60, 90, 25);
                    name.setBounds(110, 60, 110, 25);
                    PuestoTag.setBounds(20, 100, 100, 25);
                    puesto.setBounds(110, 100, 120, 25);
                    SalarioTag.setBounds(20, 140, 120, 25);
                    salario.setBounds(110, 140, 80, 25);
                    insertar.setBounds(250,300, 120, 40);

                    ActionListener inserta = e1 -> {
                        Insertar(EmployeeID.getText(), name.getText(), puesto.getText(), salario.getText() );
                        EmployeeID.setText("");
                        name.setText("");
                        puesto.setText("");
                        salario.setText("");
                        de.dispose();
                        CargarTabla(modelo);
                    };
                    insertar.addActionListener(inserta);
                    de.add(IDtag);
                    de.add(EmployeeID);
                    de.add(NombreTag);
                    de.add(name);
                    de.add(PuestoTag);
                    de.add(puesto);
                    de.add(SalarioTag);
                    de.add(salario);
                    de.add(insertar);

                    de.setSize(500,400);
                    de.setModal(true);
                    de.setVisible(true);
                }

                if (e.getSource() == boton_update) {

                    if(tabla1.getSelectedRow() != -1) {
                        JDialog de = new JDialog(ventana, "Update");
                        de.setLayout(null);

                        String row = tabla1.getValueAt(tabla1.getSelectedRow(), 0).toString();
                        JLabel IDtag = new JLabel("EmployeeID:");
                        JTextField EmployeeID = new JTextField(row);
                        EmployeeID.setEnabled(false);
                        JLabel NombreTag = new JLabel("full name:");
                        JTextField name = new JTextField();
                        JLabel PuestoTag = new JLabel("Puesto:");
                        JTextField puesto = new JTextField();
                        JLabel SalarioTag = new JLabel("Salario");
                        JTextField salario = new JTextField();
                        JButton actuaizar = new JButton("Actuaizar");

                        IDtag.setBounds(20, 20, 90, 25);
                        EmployeeID.setBounds(110, 20, 40, 25);
                        NombreTag.setBounds(20, 60, 90, 25);
                        name.setBounds(110, 60, 110, 25);
                        PuestoTag.setBounds(20, 100, 100, 25);
                        puesto.setBounds(110, 100, 120, 25);
                        SalarioTag.setBounds(20, 140, 120, 25);
                        salario.setBounds(110, 140, 80, 25);
                        actuaizar.setBounds(250, 300, 120, 40);

                        ActionListener actualiza = e1 -> {
                            Actualizar(EmployeeID.getText(), name.getText(), puesto.getText(), salario.getText());
                            EmployeeID.setText("");
                            name.setText("");
                            puesto.setText("");
                            salario.setText("");
                            CargarTabla(modelo);
                            de.dispose();
                        };

                        actuaizar.addActionListener(actualiza);

                        de.add(IDtag);
                        de.add(EmployeeID);
                        de.add(NombreTag);
                        de.add(name);
                        de.add(PuestoTag);
                        de.add(puesto);
                        de.add(SalarioTag);
                        de.add(salario);
                        de.add(actuaizar);

                        de.setSize(500, 400);
                        de.setModal(true);
                        de.setVisible(true);
                    }
                }
            }
        };

        KeyListener accionBuscar = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (buscador.getText().length() != 0) {
                    QueryBuscador(modelo, buscador.getText());
                }
            }
        };

        buscador.addKeyListener(accionBuscar);
        mostrar.addActionListener(Botoneslistener);
        boton_delete.addActionListener(Botoneslistener);
        boton_insert.addActionListener(Botoneslistener);
        boton_update.addActionListener(Botoneslistener);

        mostrar.setBounds(30 , 25,50, 50);
        boton_update.setBounds(85, 25, 50, 50);
        boton_insert.setBounds(140, 25 , 50 , 50);
        boton_delete.setBounds(195, 25 , 50, 50);
        scroll.setBounds(30, 100, 500, 250);
        tabla1.setBounds(30, 100, 500, 250);
        buscar.setBounds(300, 25, 65, 35);
        buscador.setBounds(300, 55, 125, 28);



        ventana.add(mostrar);
        ventana.add(boton_update);
        ventana.add(boton_delete);
        ventana.add(boton_insert);
        ventana.add(scroll);
        ventana.add(buscar);
        ventana.add(buscador);

        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null);
    }

}
