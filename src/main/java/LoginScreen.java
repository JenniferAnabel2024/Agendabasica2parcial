
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    
 // Constructor 
    public LoginScreen() {
        setTitle("Agenda Básica - Inicio");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 filas, 1 columna, 10px de espacio

        JButton btnRegistro = new JButton("Registrarse");
        JButton btnLogin = new JButton("Iniciar Sesión");

        panel.add(btnRegistro);
        panel.add(btnLogin);

        add(panel);

        setVisible(true); // Mostrar la ventana

        // Acción para el botón "Registrarse"
        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarRegistro(); // Llama al método para mostrar el registro
            }
        });

        // Acción para el botón "Iniciar Sesión"
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarLogin();
            }
        });
    }

    // Método para mostrar la ventana de registro
    private void mostrarRegistro() {
        new RegistroScreen(); // Crear y mostrar la ventana de registro
        dispose(); // Opcional: cerrar la ventana de inicio
    }

    // Método para mostrar la ventana de login
    private void mostrarLogin() {
        JOptionPane.showMessageDialog(this, "Ventana de Login (por implementar)");
        // Aquí más adelante vamos a abrir la pantalla de inicio de sesión
    }

    public static void main(String[] args) {
        new LoginScreen(); // Crear y mostrar la pantalla de inicio
    }
}