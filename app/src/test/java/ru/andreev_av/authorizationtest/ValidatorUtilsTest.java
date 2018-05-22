package ru.andreev_av.authorizationtest;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.andreev_av.authorizationtest.utils.ValidatorUtils;

public class ValidatorUtilsTest {

    @Test
    public void validEmailTest() {

        List<String> emails = new ArrayList<>();
        emails.add("andreev@mail.ru");
        emails.add("andreev-100@yandex.ru");
        emails.add("andreev.100@gmail.com");
        emails.add("andreev111@andreev.ru");
        emails.add("andreev-100@andreev.net");
        emails.add("andreev.100@andreev.com.ru");
        emails.add("andreev@1.com");
        emails.add("andreev@gmail.com.com");
        emails.add("andreev+100@gmail.com");
        emails.add("andreev-100@yahoo-test.com");

        for (String email : emails) {
            boolean valid = ValidatorUtils.checkValidEmail(email);
            Assert.assertEquals(true, valid);
        }
    }

    @Test
    public void invalidEmailTest() {

        List<String> emails = new ArrayList<>();
        emails.add("andreev");
        emails.add("andreev@.com.my");
        emails.add("andreev123@gmail.a");
        emails.add("andreev123@.com");
        emails.add("andreev123@.com.com");
        emails.add(".andreev@andreev.ru");
        emails.add("andreev()*@gmail.com");
        emails.add("andreev@%*.com");
        emails.add("andreev..2002@gmail.com");
        emails.add("andreev.@gmail.com");
        emails.add("andreev@andreev@gmail.com");
        emails.add("andreev@gmail.com.1a");

        for (String email : emails) {
            boolean valid = ValidatorUtils.checkValidEmail(email);
            Assert.assertEquals(false, valid);
        }
    }

    @Test
    public void validPasswordTest() {

        List<String> passwords = new ArrayList<>();
        passwords.add("A4Ne#v");
        passwords.add("12Andreev#$");
        passwords.add("$http###andreev87%Ru");
        passwords.add("%ThisIs$tr0ngPass%");
        passwords.add("%ThisIs$tr0ngPass%ManyManyManyMany");
        passwords.add("andreevRu12*");

        for (String password : passwords) {
            boolean valid = ValidatorUtils.checkValidPassword(password);
            Assert.assertEquals(true, valid);
        }
    }

    @Test
    public void invalidPasswordTest() {

        List<String> passwords = new ArrayList<>();
        passwords.add("mY1A@");
        passwords.add("12andreev#$");
        passwords.add("$http###andreev%Ru");
        passwords.add("andreev12@");
        passwords.add("ANdreEv$$");
        passwords.add("andreev12$");

        for (String password : passwords) {
            boolean valid = ValidatorUtils.checkValidPassword(password);
            Assert.assertEquals(false, valid);
        }
    }


}