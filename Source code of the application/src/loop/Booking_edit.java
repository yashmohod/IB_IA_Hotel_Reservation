/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Frank
 */
public class Booking_edit extends javax.swing.JFrame {

    public byte[] image_indentification= null;
    public String filename = null;
    
    
    
     public Connection cn;
    public Statement st;
    
   /* 
    public void conection()    
    {    
        
       try
        {
             cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loop_hotel?zeroDateTimeBehavior=convertToNull", "root","");
             st = cn.createStatement();
             System.out.println("conected");
            
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null,"not conected" );
        }
    }
    */
    
    
    
    
    //update the tables 
    public void update()
    {
        get_set_data pop = new get_set_data();
        pop.main(cn);
        
    }
   
    /// display all the details 
    public void display_deatils(String booking_ind ) 
    {
        try 
        {
            String sql = "SELECT * FROM `bookings` WHERE `Ind_book` = '"+booking_ind+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                firstname_textbox.setText(rs.getString("Booked_by_firstname"));
                lastname_textbox.setText(rs.getString("Booked_by_lastname"));
                String DF = rs.getString("Booked_from");
                String DT = rs.getString("Booked_to");
                System.out.println(DF+"  "+DT);
                Date date1;  
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(DF);
                    date_from_datechooser.setDate(date1);
                } catch (ParseException ex) {
                    Logger.getLogger(Booking_edit.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date date2;
                try {
                    date2 = new SimpleDateFormat("yyyy-MM-dd").parse(DT);
                    date_to_datechooser.setDate(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(Booking_edit.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                noAdult_textbox.setText(rs.getString("No. of Adults"));
                noChildren_textbox.setText(rs.getString("No. of children"));
                
                
              //  byte[] img = rs.getBytes("identification");
                
                
                
                //Blob blob = rs.getBlob("identification");
               // byte[] img= blob.getBytes(1, (int)blob.length());
                byte[] img= rs.getBytes("identification");
                if(img==null){continue;}
                else{
                image_indentification= img;
                ImageIcon image = new ImageIcon(img);
                Image im  = image.getImage();
                Image myimg = im.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newimage = new ImageIcon(myimg);
                foto.setIcon(newimage);}
            }
        }
        catch(SQLException e )
        {System.out.println(e);}
    }
    
    public  void Update_edit(String ftName , String ltName, String datefrom , String dateto, String num_adult, String num_children, String bookind)
    {
        
         try {
                String sql = "UPDATE `bookings` SET `Booked_by_firstname`='"+ftName+"',`Booked_by_lastname`='"+ltName+"',`Booked_from`='"+datefrom+"',`Booked_to`='"+dateto+"',`No. of Children`='"+num_children+"',`No. of Adults`='"+num_adult+"',`identification`='"+image_indentification+"' WHERE  `Ind_book` ='"+bookind+"'";
                PreparedStatement ps = cn.prepareStatement(sql);
                int rs = ps.executeUpdate();
                if(rs > 0  ){JOptionPane.showMessageDialog(null, "submitted");}
                

                

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
    }
    
    public void get_edited_data()
    {
         // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //get all the information
        String firstname = firstname_textbox.getText();
        String lastname = lastname_textbox.getText();
        String datefrom = "";
        String dateto = "";
        datefrom = sdf.format(date_from_datechooser.getDate());
        dateto = sdf.format(date_to_datechooser.getDate());
        String num_adult = noAdult_textbox.getText();
        String num_children = noChildren_textbox.getText();

        // check for missing information
        String c = "";
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        String Today = sdf.format(today);

        boolean c1 = firstname.equals(c);
        boolean c2 = lastname.equals(c);
        boolean c3 = datefrom.equals("00-00-0000");
        boolean c4 = dateto.equals("00-00-0000");
        boolean c34 = datefrom.compareTo(dateto)>0;
        boolean c5 = num_adult.equals(c);
        boolean c6 = num_children.equals(c);
        boolean ct = false;
        if (c1==false && c2==false && c3==false && c4==false && c34==false && c5==false && c6==false)
        {
            ct = true;
        }
        // show according to the checks

        if (c1){error1_lable.setVisible(true);}
        if (c2){error2_lable.setVisible(true);}
        if (c3){error3_lable.setVisible(true);}
        if (c4){error4_lable.setVisible(true);}
        if (c34){error3_lable.setVisible(true);
        error4_lable.setVisible(true);}
        if (c5){error5_lable.setVisible(true);}
        if (c6){error6_lable.setVisible(true);}

        
        //update the datebase
        if (ct)
        {
           ///Update_edit(firstname, lastname,datefrom,dateto,num_adult,num_children,BookIndex);
           new Booking_room_change(BookIndex,firstname, lastname,datefrom,dateto,num_adult,num_children,image_indentification, cn).setVisible(true);
        }
        
        
        
        this.dispose();
    }
    
    public Booking_edit() {
        initComponents();
        this.setLocationRelativeTo(null);
        disable();
        update();
    }
    public String BookIndex= "";
    public Booking_edit(String BookingIndex, Connection con) {
        initComponents();
        this.setLocationRelativeTo(null);
        cn = con;
        disable();
        update();
        display_deatils(BookingIndex);
        BookIndex = BookingIndex;
    }
    public void disable()
    {
        error1_lable.setVisible(false);
        error2_lable.setVisible(false);
        error3_lable.setVisible(false);
        error4_lable.setVisible(false);
        error5_lable.setVisible(false);
        error6_lable.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        deleteSelectedBooking_button = new javax.swing.JButton();
        firstName_lable = new javax.swing.JLabel();
        lastName_lable = new javax.swing.JLabel();
        dateFrom_label = new javax.swing.JLabel();
        dateTo_label = new javax.swing.JLabel();
        noFoAdults_label = new javax.swing.JLabel();
        noFoChildren_label = new javax.swing.JLabel();
        identificationNo_lable = new javax.swing.JLabel();
        firstname_textbox = new javax.swing.JTextField();
        lastname_textbox = new javax.swing.JTextField();
        noAdult_textbox = new javax.swing.JTextField();
        noChildren_textbox = new javax.swing.JTextField();
        deleteSelectedBooking_button1 = new javax.swing.JButton();
        error1_lable = new javax.swing.JLabel();
        error2_lable = new javax.swing.JLabel();
        error3_lable = new javax.swing.JLabel();
        error4_lable = new javax.swing.JLabel();
        error5_lable = new javax.swing.JLabel();
        error6_lable = new javax.swing.JLabel();
        foto = new javax.swing.JLabel();
        date_from_datechooser = new com.toedter.calendar.JDateChooser();
        date_to_datechooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        deleteSelectedBooking_button.setBackground(new java.awt.Color(51, 102, 255));
        deleteSelectedBooking_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deleteSelectedBooking_button.setForeground(new java.awt.Color(255, 255, 255));
        deleteSelectedBooking_button.setText("Next");
        deleteSelectedBooking_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedBooking_buttonActionPerformed(evt);
            }
        });

        firstName_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable.setText(" First Name");

        lastName_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastName_lable.setForeground(new java.awt.Color(51, 102, 255));
        lastName_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lastName_lable.setText("Last Name");

        dateFrom_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dateFrom_label.setForeground(new java.awt.Color(51, 102, 255));
        dateFrom_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateFrom_label.setText("Date From");
        dateFrom_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateFrom_labelMouseClicked(evt);
            }
        });

        dateTo_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dateTo_label.setForeground(new java.awt.Color(51, 102, 255));
        dateTo_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateTo_label.setText("Date To ");
        dateTo_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateTo_labelMouseClicked(evt);
            }
        });

        noFoAdults_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoAdults_label.setForeground(new java.awt.Color(51, 102, 255));
        noFoAdults_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        noFoAdults_label.setText("No.of Adults ");

        noFoChildren_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoChildren_label.setForeground(new java.awt.Color(51, 102, 255));
        noFoChildren_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        noFoChildren_label.setText("No.of  Children");

        identificationNo_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        identificationNo_lable.setForeground(new java.awt.Color(51, 102, 255));
        identificationNo_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        identificationNo_lable.setText("Identification");

        firstname_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstname_textbox.setForeground(new java.awt.Color(0, 102, 255));

        lastname_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastname_textbox.setForeground(new java.awt.Color(0, 102, 255));

        noAdult_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noAdult_textbox.setForeground(new java.awt.Color(0, 102, 255));

        noChildren_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noChildren_textbox.setForeground(new java.awt.Color(0, 102, 255));

        deleteSelectedBooking_button1.setBackground(new java.awt.Color(51, 102, 255));
        deleteSelectedBooking_button1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deleteSelectedBooking_button1.setForeground(new java.awt.Color(255, 255, 255));
        deleteSelectedBooking_button1.setText("Change ID.");
        deleteSelectedBooking_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedBooking_button1ActionPerformed(evt);
            }
        });

        error1_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error1_lable.setForeground(new java.awt.Color(255, 0, 0));
        error1_lable.setText("*");

        error2_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error2_lable.setForeground(new java.awt.Color(255, 0, 0));
        error2_lable.setText("*");

        error3_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error3_lable.setForeground(new java.awt.Color(255, 0, 0));
        error3_lable.setText("*");

        error4_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error4_lable.setForeground(new java.awt.Color(255, 0, 0));
        error4_lable.setText("*");

        error5_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error5_lable.setForeground(new java.awt.Color(255, 0, 0));
        error5_lable.setText("*");

        error6_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error6_lable.setForeground(new java.awt.Color(255, 0, 0));
        error6_lable.setText("*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(identificationNo_lable)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(firstname_textbox)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteSelectedBooking_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(error1_lable)
                        .addContainerGap())
                    .addComponent(deleteSelectedBooking_button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(noFoAdults_label)
                    .addComponent(dateTo_label)
                    .addComponent(lastName_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstName_lable)
                    .addComponent(dateFrom_label)
                    .addComponent(noFoChildren_label))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noAdult_textbox)
                    .addComponent(lastname_textbox)
                    .addComponent(noChildren_textbox)
                    .addComponent(date_from_datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_to_datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error2_lable)
                    .addComponent(error3_lable)
                    .addComponent(error4_lable)
                    .addComponent(error5_lable)
                    .addComponent(error6_lable))
                .addGap(187, 187, 187))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(error1_lable)
                .addGap(439, 439, 439)
                .addComponent(deleteSelectedBooking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(firstname_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstName_lable))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lastName_lable))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastname_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(error2_lable))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error3_lable)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(dateFrom_label)
                        .addGap(17, 17, 17))
                    .addComponent(date_from_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(dateTo_label)
                        .addGap(18, 18, 18)
                        .addComponent(noFoAdults_label))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(error4_lable)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(date_to_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(error5_lable)
                            .addComponent(noAdult_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noFoChildren_label)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(error6_lable)
                            .addComponent(noChildren_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(deleteSelectedBooking_button1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(identificationNo_lable)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteSelectedBooking_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedBooking_button1ActionPerformed
        // TODO add your handling code here:
        try{
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();

        try
        {
            File image = new File(filename);
            FileInputStream fis =   new FileInputStream(image);
            ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(foto.getWidth(),foto.getHeight(), Image.SCALE_SMOOTH));
            foto.setIcon(imageicon);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte [] buf =  new byte[1024];
            for(int readNum; (readNum = fis.read(buf))!= -1;)
            {
                bos.write(buf,0,readNum);
            }
            image_indentification = bos.toByteArray();
        }
        catch(IOException e )
        {
            JOptionPane.showMessageDialog(null, e +"");

        }

        System.out.println(Arrays.toString(image_indentification));}
        catch(NullPointerException e)
        {
            System.out.print(e +" at 519");
        }
    }//GEN-LAST:event_deleteSelectedBooking_button1ActionPerformed

    private void dateTo_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTo_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTo_labelMouseClicked

    private void dateFrom_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFrom_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFrom_labelMouseClicked

    private void deleteSelectedBooking_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedBooking_buttonActionPerformed
        // TODO add your handling code here:

        get_edited_data();

    }//GEN-LAST:event_deleteSelectedBooking_buttonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Booking_edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking_edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking_edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking_edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking_edit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel dateFrom_label;
    private javax.swing.JLabel dateTo_label;
    private com.toedter.calendar.JDateChooser date_from_datechooser;
    private com.toedter.calendar.JDateChooser date_to_datechooser;
    private javax.swing.JButton deleteSelectedBooking_button;
    private javax.swing.JButton deleteSelectedBooking_button1;
    private javax.swing.JLabel error1_lable;
    private javax.swing.JLabel error2_lable;
    private javax.swing.JLabel error3_lable;
    private javax.swing.JLabel error4_lable;
    private javax.swing.JLabel error5_lable;
    private javax.swing.JLabel error6_lable;
    private javax.swing.JLabel firstName_lable;
    private javax.swing.JTextField firstname_textbox;
    private javax.swing.JLabel foto;
    private javax.swing.JLabel identificationNo_lable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lastName_lable;
    private javax.swing.JTextField lastname_textbox;
    private javax.swing.JTextField noAdult_textbox;
    private javax.swing.JTextField noChildren_textbox;
    private javax.swing.JLabel noFoAdults_label;
    private javax.swing.JLabel noFoChildren_label;
    // End of variables declaration//GEN-END:variables
}
