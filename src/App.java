import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
/**
 * Класс для создания окна, в котором пользователь может ввести данные о человеке
 * и отправить их для создания экземпляра класса {@link Human}
 * @author Андрей Помошников
  *@version 1.0
 */
public class App extends JFrame {
    /** Поле ввода имени */
    private final JTextField firstNameTextField;
    /** Поле ввода фамилии */
    private final JTextField lastNameTextField;
    /** Поле ввода отчества */
    private final JTextField middleNameTextField;
    /** Календарь */
    private final JCalendar calendar;

    /**
     * Конструктор класса.
     * Создает окно для ввода данных пользователя.
     */
    public App() {
        super("Информализатор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Пытаемся установить одну из стилизаций
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        // Стилизуем окно под персональный фон и шрифт
        Font font = new Font("Verdana", Font.PLAIN, 14);
        Color background = new Color(238, 238, 238);
        Color foreground = new Color(50, 50, 50);
        //Добавляем на сеточную панель необходимые поля и метки
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(background);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        JLabel lastNameLabel = new JLabel("Фамилия:");
        lastNameLabel.setFont(font);
        lastNameLabel.setForeground(foreground);
        panel.add(lastNameLabel, c);
        lastNameTextField = new JTextField(20);
        lastNameTextField.setFont(font);
        lastNameTextField.setBackground(background);
        lastNameTextField.setForeground(foreground);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(lastNameTextField, c);

        c.gridx = 0;
        c.gridy = 1;
        JLabel firstNameLabel = new JLabel("Имя:");
        firstNameLabel.setFont(font);
        firstNameLabel.setForeground(foreground);
        panel.add(firstNameLabel, c);
        firstNameTextField = new JTextField(20);
        firstNameTextField.setFont(font);
        firstNameTextField.setBackground(background);
        firstNameTextField.setForeground(foreground);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(firstNameTextField, c);

        c.gridx = 0;
        c.gridy = 2;
        JLabel middleNameLabel = new JLabel("Отчество:");
        middleNameLabel.setFont(font);
        middleNameLabel.setForeground(foreground);
        panel.add(middleNameLabel, c);
        middleNameTextField = new JTextField(20);
        middleNameTextField.setFont(font);
        middleNameTextField.setBackground(background);
        middleNameTextField.setForeground(foreground);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(middleNameTextField, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel birthDateLabel = new JLabel("Дата рождения:");
        birthDateLabel.setFont(font);
        birthDateLabel.setForeground(foreground);
        panel.add(birthDateLabel, c);
        calendar = new JCalendar();
        calendar.setFont(font);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(calendar, c);

        JButton sendButton = new JButton("Выполнить");
        sendButton.setFont(font);
        sendButton.setBackground(new Color(10, 24, 92));
        sendButton.setForeground(background);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Наивная валидация введенного ФИО
                String namePattern = "^\\p{L}+$";

                String lastName = lastNameTextField.getText().trim();
                if (!lastName.matches(namePattern)) {
                    JOptionPane.showMessageDialog(App.this, "Введите корректную фамилию", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String firstName = firstNameTextField.getText().trim();
                if (!firstName.matches(namePattern)) {
                    JOptionPane.showMessageDialog(App.this, "Введите корректное имя", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String middleName = middleNameTextField.getText().trim();
                if (!middleName.matches(namePattern)) {
                    JOptionPane.showMessageDialog(App.this, "Введите корректное отчество", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Преобразование к валидному регистру(Заглвная буква+строчные)
                firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
                lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
                middleName = middleName.substring(0, 1).toUpperCase() + middleName.substring(1).toLowerCase();

                String birthDate = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate());
                // Валидация введеных данных(возможно устарела)
                if (firstName.isEmpty() || lastName.isEmpty() || middleName.isEmpty() ||  birthDate.equals("")) {
                    JOptionPane.showMessageDialog(App.this, "Заполните все поля", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Human person = new Human();

                try {
                    person = new Human(lastName + " " + firstName + " " + middleName, birthDate);
                }
                catch (DateTimeException dex)
                {
                    JOptionPane.showMessageDialog(App.this, dex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    JLabel messageLabel = new JLabel(person.toString());
                    messageLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
                    JOptionPane.showMessageDialog(App.this, messageLabel, "Информализатор", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (DateTimeException ex)
                {
                    JOptionPane.showMessageDialog(App.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(sendButton, c);

        setContentPane(panel);
        //Установка минимального размера окна
        setMinimumSize(new Dimension(520, 450));
        pack();
        //Центрирование
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
