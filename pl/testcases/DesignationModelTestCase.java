import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;

class DesignationModelTestCase extends JFrame
{
private Container container;
private JTable table;
private JScrollPane jsp;
private DesignationModel designationModel;
DesignationModelTestCase()
{
container=getContentPane();
container.setLayout(new BorderLayout());
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container.add(jsp);
setSize(500,600);
setLocation(100,200);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase a=new DesignationModelTestCase();
}
}
