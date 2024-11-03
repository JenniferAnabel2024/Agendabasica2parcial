import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroScreen extends JFrame {
  private JTextField txtNick;
    private JPasswordField txtPassword;

    // Constructor
    public RegistroScreen() {
        setTitle("Agenda Básica - Registro");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Elementos del formulario
        JLabel lblNick = new JLabel("Nick:");
        txtNick = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();

        JButton btnRegistrarse = new JButton("Registrarse");
        JButton btnAtras = new JButton("Volver");

        // Añadir los componentes al panel
        panel.add(lblNick);
        panel.add(txtNick);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(btnRegistrarse);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(btnAtras);

        add(panel);

        // ActionListener para el botón "Registrarse"
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener la contraseña
                    String password = new String(txtPassword.getPassword());

                    // Regex para validar la contraseña
                    String regex = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[$@$!%*?&]).{12,}$";

                    if (!password.matches(regex)) {
                        throw new Exception("La contraseña no cumple los requisitos.");
                    }

                    // Si la validación es exitosa
                    JOptionPane.showMessageDialog(null, "Registro exitoso");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de registro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener para el botón "Volver"
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la pantalla de inicio
                new LoginScreen(); // Crear una nueva instancia de LoginScreen
                dispose(); // Cierra la ventana actual
            }
        });

        setVisible(true); // Mostrar la ventana
    }

    public static void main(String[] args) {
        new RegistroScreen(); // Crear y mostrar la pantalla de registro
    }
}