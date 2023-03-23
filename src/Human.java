import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
/**
 * Класс, представляющий человека со свойствами <b>фамилии</b>, <b>имени</b>, <b>отчества</b> и <b>даты рождения</b>
 * @author Андрей Помошников
 * @version 1.0
 */
public class Human {
    /** Поле фамилии */
    private final String lastName;
    /** Поле имени */
    private final String firstName;
    /** Поле отчества */
    private final String middleName;
    /** Поле даты рождения */
    private final LocalDate birthDate;
    /**
     * Конструктор класса Human
     * @param fullName полное имя в формате "Фамилия Имя Отчество"
     * @param birthDateString строковое представление даты рождения в формате "dd.MM.yyyy"
     * @throws DateTimeException если что-то совершенно неожиданное случилось
     * @see Human#Human()
     */
    public Human(String fullName, String birthDateString) throws DateTimeException {
        String[] nameParts = fullName.split(" ");
        this.lastName = nameParts[0];
        this.firstName = nameParts[1];
        this.middleName = nameParts[2];
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            this.birthDate = LocalDate.parse(birthDateString, formatter);
        }
        catch (DateTimeException dex)
        {
           throw new DateTimeException("Actually, unknown trouble");
        }
    }
    /**
     * Конструктор по умолчанию
     * @see Human#Human(String, String)
     * */
    public Human() {
        lastName = "";
        firstName = "";
        middleName = "";
        birthDate = null;
    }
    /**
     * Метод, возвращающий инициалы имени и отчества
     * @return строка, содержащая инициалы имени и отчества, разделенные точками
     */
    public String getInitials() {
        return String.format("%s.%s.", firstName.charAt(0), middleName.charAt(0));
    }

    /**
     * Метод, определяющий пол человека по отчеству
     * @return строка "Ж" для женщин и "М" для мужчин
     */
    public String getGender() {
        if (middleName.endsWith("а") || middleName.endsWith("зы") ) {
            return "Ж"; // Female
        } else {
            return "М"; // Male
        }
    }

    /**
     * Метод, вычисляющий возраст человека в годах на текущую дату
     * @return полный возраст человека в годах
     */
    public int getAge() {
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }

    /**
     * Метод, возвращающий склонение слова "год" в зависимости от возраста человека
     * @return строка, содержащая слово "год", "года" или "лет"
     */
    public String getAgeSuffix() {
        int age = getAge();
        if (age % 10 == 1 && age != 11) {
            return "год";
        } else if (age % 10 >= 2 && age % 10 <= 4 && !(age >= 12 && age <= 14)) {
            return "года";
        } else {
            return "лет";
        }
    }

    /**
     * Метод, проверяющий, является ли дата рождения действительной (не позже текущей даты)
     * @return true, если дата рождения действительная, и false в противном случае
     */
    public boolean isValidBirthDate() {
        LocalDate now = LocalDate.now();
        return !birthDate.isAfter(now);
    }

    /**
     * Возвращает строковое представление объекта Human, которое включает в себя фамилию, инициалы, пол и возраст
     * @return строковое представление объекта Human, в формате "Фамилия И.О. Пол Возраст лет/года/год"
     * @throws DateTimeException если дата рождения является недействительной (из будущего)
     */
    @Override
    public String toString() throws DateTimeException {
        if (!isValidBirthDate()) {
            throw  new DateTimeException("Вы ввели дату из будущего, попробуйте еще раз ^_^");
        }
        return String.format("%s %s   %s   %d %s", lastName, getInitials(), getGender(), getAge(), getAgeSuffix());
    }
}
