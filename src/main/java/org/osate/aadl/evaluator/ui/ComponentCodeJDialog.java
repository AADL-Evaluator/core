package org.osate.aadl.evaluator.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import org.jdesktop.swingx.JXHeader;
import org.osate.aadl.evaluator.project.Component;

public class ComponentCodeJDialog extends javax.swing.JDialog 
{
    
    public ComponentCodeJDialog( java.awt.Window parent )
    {
        super( parent );
        
        initComponents();
        init();
        
        setTitle( "Component View" );
        setSize( 800 , 500 );
        setModal( true );
        setLocationRelativeTo( parent );
    }
    
    private void init()
    {
        add( new JXHeader( 
            "Component View" , 
            "It is code of the component selected."
        ) , BorderLayout.NORTH );
        
        jTextArea.setFont( new Font( Font.MONOSPACED , Font.PLAIN , 13 ) );
    }
    
    public void setComponent( Component component )
    {
        addLine( component );
    }
    
    private void addLine( Component c )
    {
        if( c == null )
        {
            return ;
        }
        
        addLine( c.getComponentExtended() );
        
        for( String line : c.getContent() )
        {
            jTextArea.append( line );
            jTextArea.append( "\n" );
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables
}
