package service;

import model.Referral;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Singleton referral manager.
 * Ensures a single referral queue and consistent referral processing.
 */
public final class ReferralService {

    private static ReferralService instance;

    private final Queue<Referral> referralQueue = new ArrayDeque<>();
    private final Path outputFile = Paths.get("data").resolve("generated_referrals.txt");

    // Private constructor enforces Singleton
    private ReferralService() {}

    // Global access point
    public static synchronized ReferralService getInstance() {
        if (instance == null) {
            instance = new ReferralService();
        }
        return instance;
    }

    public void enqueueReferral(Referral referral) {
        if (referral != null) {
            referralQueue.add(referral);
        }
    }

    public int queueSize() {
        return referralQueue.size();
    }

    /**
     * Processes the next referral in the queue.
     * Generates referral text and appends it to file.
     */
    public String processNextReferral(String suggestedRecipient) throws IOException {
        Referral referral = referralQueue.poll();
        if (referral == null) {
            return null;
        }

        String text = referral.createReferralText(suggestedRecipient);
        appendToReferralFile(text);
        return text;
    }

    private void appendToReferralFile(String content) throws IOException {
        Files.createDirectories(outputFile.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(
                outputFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(content);
        }
    }
}
