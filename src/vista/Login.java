package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public Login() {
        setTitle("Login - Inventario");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(30, 30, 80, 25);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 120, 25);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 70, 80, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 120, 25);
        add(txtPassword);

        btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(90, 110, 100, 30);
        add(btnLogin);

        // Evento botón
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void login() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(usuario, password);

        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombre());

            // Abrir ventana principal
            new Principal().setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Datos incorrectos");
        }
    }
}