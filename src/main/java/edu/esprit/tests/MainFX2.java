package edu.esprit.tests;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainFX2 {
    public static void main(String[] args) {
        // Set your secret key here
        Stripe.apiKey = "sk_test_51PD97FRrveSTogQuowJFOHZ9ddkBIZ06Hr5uOAxczHUb30rTopAPdkpCrmYcUmAbdIQZlAAqojR7aKGiEbYHzTpK00nNcJVNMv";

        try {
            // Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
            // Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }


        // Create a PaymentIntent with other payment details
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(10000L) // Amount in cents (e.g., $10.00)
                .setCurrency("usd")
                .build();

        PaymentIntent intent = null;
        try {
            intent = PaymentIntent.create(params);
        } catch (StripeException ex) {
            throw new RuntimeException(ex);
        }

        // If the payment was successful, display a success message
        System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
    }


}
