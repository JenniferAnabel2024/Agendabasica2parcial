import javax.swing.*;
import javax.swing.border.Border; // Asegúrate de importar esto
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
        panel.setLayout(new GridBagLayout()); // Usa GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Permite que los botones se expandan
        gbc.weightx = 1.0; // Expande horizontalmente
        gbc.weighty = 1.0; // Expande verticalmente

        JButton btnRegistro = new JButton("Registrarse");
        JButton btnLogin = new JButton("Iniciar Sesión");

        // Establecer un borde para los botones
        Border border = BorderFactory.createLineBorder(Color.BLACK); // Borde negro
        btnRegistro.setBorder(border);
        btnLogin.setBorder(border);

        // Configurar los botones para que ocupen la misma cantidad de espacio
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnRegistro, gbc);

        gbc.gridy = 1; // Nueva fila
        panel.add(btnLogin, gbc);

        // Cambiar el tamaño de fuente según el tamaño de la ventana
        btnRegistro.setFont(new Font("Arial", Font.PLAIN, 20));
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 20));

        // Hacer que el tamaño de los botones se ajuste al redimensionar la ventana
        btnRegistro.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarTamanoBoton(btnRegistro);
            }
        });

        btnLogin.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarTamanoBoton(btnLogin);
            }
        });

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
    }

    // Ajustar el tamaño de los botones
    private void ajustarTamanoBoton(JButton boton) {
        Dimension tamaño = boton.getSize();
        int nuevoTamano = Math.min(tamaño.width, tamaño.height) / 10; // Ajusta el tamaño de la fuente (más pequeño)
        boton.setFont(new Font("Arial", Font.PLAIN, nuevoTamano));
    }

    public static void main(String[] args) {
        new LoginScreen(); // Crear y mostrar la pantalla de inicio
    }
}
