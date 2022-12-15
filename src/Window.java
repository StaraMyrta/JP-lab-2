import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Klasa Window zawierająca wszystkie wydarzenia zachodzące w oknie programu
 */
public class Window extends JFrame implements ActionListener {

    //deklaracja listy przechowującej informacje o loginach i hasłach użytkowników,
    //pól tekstowych, pola na hasło, przycisków i napisów,
    //licznika nieudanych prób zalogowania

    public ArrayList<LoginPasswordSetter> users = new ArrayList<>();

    JButton bLogin, bCancel;
    JTextField tfLogin;
    JLabel lLogin,lPassword,lSuccess;
    JPasswordField pfPassword;

    int wrongPassword=0;

    public Window() {
        setSize(500, 230);
        setLocation(450,250);
        setTitle("Logowanie");
        setLayout(null);
        getContentPane().setBackground(new Color(100, 160, 160));

        //deklaracja i specyfika przycisku akceptacji
        bLogin = new JButton("Log in");
        URL LGiconURL = getClass().getResource("/icons/accept.png");
        assert LGiconURL != null : "accept icon not found";
        bLogin.setIcon(new ImageIcon(LGiconURL));
        bLogin.setBounds(70, 100, 150, 70);
        bLogin.setFont(new Font("Dialog", Font.ITALIC, 22));
        bLogin.setBackground(new Color(19, 94, 36));
        bLogin.setForeground(new Color(195, 195, 195));

        //deklaracja i specyfikacja przycisku rezygnacji
        bCancel = new JButton("Cancel");
        URL CNiconURL = getClass().getResource("/icons/cancel.png");
        assert CNiconURL != null : "cancel icon not found";
        bCancel.setIcon(new ImageIcon(CNiconURL));
        bCancel.setBounds(260, 100, 150, 70);
        bCancel.setFont(new Font("Dialog", Font.ITALIC, 22));
        bCancel.setBackground(new Color(153, 0, 0));
        bCancel.setForeground(new Color(195, 195, 195));


        //opis pola tekstowego loginu
        lLogin = new JLabel("Login:");
        lLogin.setBounds(80, 30, 70, 20);
        lLogin.setFont(new Font("Dialog", Font.ITALIC, 15));

        //deklaracja pola tekstowego loginu
        tfLogin = new JTextField();
        tfLogin.setBounds(180, 30, 220, 20);
        tfLogin.setForeground(Color.BLACK);

        //opis pola zahasłowanego
        lPassword = new JLabel("Password:");
        lPassword.setBounds(80, 60, 90, 20);
        lPassword.setFont(new Font("Dialog", Font.ITALIC, 15));

        //deklaracja pola zahasłowanego
        pfPassword = new JPasswordField();
        pfPassword.setBounds(180, 60, 220, 20);

        //informacja o udanym zalogowaniu
        lSuccess = new JLabel("Logowanie powiodło się!");
        lSuccess.setBounds(70, 60, 500, 50);
        lSuccess.setFont(new Font("Dialog", Font.ITALIC, 30));

        //dodanie wszystkich elementów etapu logowania do okna
        add(bLogin);
        add(bCancel);
        add(lLogin);
        add(tfLogin);
        add(lPassword);
        add(pfPassword);

        //dodanie metody ActionListener do wydarzeń naciśnięcia przycisków akceptacji i rezygnacji
        bCancel.addActionListener(this);
        bLogin.addActionListener(this);

        //deklaracja kombinacji loginów i haseł
        LoginPasswordSetter user1 = new LoginPasswordSetter();
        user1.setLogin("admin");
        user1.setPassword(new char[]{'a', 'd', 'm', 'i', 'n'});
        users.add(user1);

        LoginPasswordSetter user2 = new LoginPasswordSetter();
        user2.setLogin("kapitan");
        user2.setPassword(new char[]{'b', 'o', 'm','b','a'});
        users.add(user2);

        LoginPasswordSetter user3 = new LoginPasswordSetter();
        user3.setLogin("_xXx_");
        user3.setPassword(new char[]{'p', 'r', 'r', 'r'});
        users.add(user3);
    }

    //metoda sprawdzająca ilość nieudanych prób
    void check(int number){
        if(number==3) {
            dispose();
        }
    }

    String login;
    char[] password = new char[0];

    //deklaracja listy przechowującej obiekty typu LoginPasswordSetter porównywanych do
    //wprowadzonych przez użytkownika danych
    ArrayList<LoginPasswordSetter> usersWithTheSameLogin = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean isPasswordCorrect = false;

        Object source = e.getSource();

        if(source==bLogin){
            //pobranie zmiennej typu String z pola tekstowego loginu
            login=tfLogin.getText();

            //pobranie zmiennej typu char[] z pola zahasłowanego
            password= pfPassword.getPassword();

            for (LoginPasswordSetter user : users) {
                if (Objects.equals(login, user.login)) {
                    usersWithTheSameLogin.add(user);
                }
                if (usersWithTheSameLogin.isEmpty()) {
                    getContentPane().setBackground(new Color(200, 40, 50));
                    wrongPassword++;
                    check(wrongPassword);
                    break;
                }
            }



            for (LoginPasswordSetter user:usersWithTheSameLogin) {
                int loops = 0;
                    if (user.password.length != password.length) {
                        System.out.println("niezgodna dlugosc hasel");
                        getContentPane().setBackground(new Color(200,40,50));
                        wrongPassword++;
                        System.out.println("Próba nr: " + wrongPassword);
                        check(wrongPassword);
                        break;
                    }
                    for (char znak : password) {
                        if (znak == user.password[loops]) {
                            isPasswordCorrect = true;

                        } else {
                            Window.this.setBackground(Color.red);
                            isPasswordCorrect = false;
                            break;
                        }
                        loops++;
                    }
                //System.out.println("hasło: " + isPasswordCorrect);
                //System.out.println("próba nr: " + wrongPassword);
                    if (user.password.length == password.length) {
                        if (isPasswordCorrect) {

                            getContentPane().setBackground(new Color(10,150,80));

                            remove(bLogin);
                            remove(bCancel);
                            remove(lLogin);
                            remove(tfLogin);
                            remove(lPassword);
                            remove(pfPassword);

                            add(lSuccess);

                        }else {
                            System.out.println("haslo niepoprawne");
                            getContentPane().setBackground(new Color(200,40,50));
                            wrongPassword++;
                            System.out.println("Próba nr: " + wrongPassword);
                            check(wrongPassword);

                            break;
                        }
                    }
                }
            }


        if (source==bCancel){
            tfLogin.setText(null);
            pfPassword.setText(null);
            getContentPane().setBackground(new Color(160,100,100));
            JLabel cancelPressed = new JLabel("Cancel button has been pressed");
            cancelPressed.setBounds(150,5, 200, 20);
            lPassword.setFont(new Font("Dialog", Font.ITALIC, 15));
            add(cancelPressed);
        }
    }
}







