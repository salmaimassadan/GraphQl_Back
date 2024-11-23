package ma.projet.graph;

import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.TypeCompte;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeTransaction;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class GraphApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CompteRepository compteRepository, TransactionRepository transactionRepository) {
		return args -> {
			for (long i = 1; i <= 10; i++) {
				// Create Compte
				Compte compte = new Compte();
				compte.setSolde(1000.0 + (i * 50));
				compte.setDateCreation(LocalDate.now());
				compte.setType(i % 2 == 0 ? TypeCompte.COURANT : TypeCompte.EPARGNE);
				compteRepository.save(compte);

				// Create 3 Transactions for each Compte
				for (int j = 1; j <= 3; j++) {
					Transaction transaction = new Transaction();
					transaction.setMontant(100.0 + (j * 50)); // Example amounts
					transaction.setDate(LocalDate.now().plusDays(j)); // Increment days for each transaction
					transaction.setType(j % 2 == 0 ? TypeTransaction.RETRAIT : TypeTransaction.DEPOT); // Alternate types
					transaction.setCompte(compte); // Set the associated Compte

					transactionRepository.save(transaction);
				}
			}
			System.out.println("Creation done with transactions.");
		};
	}
}
