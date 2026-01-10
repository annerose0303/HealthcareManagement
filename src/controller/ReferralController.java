package controller;

import model.Referral;
import service.ReferralService;

import java.io.IOException;

public class ReferralController {

    private final ReferralService referralService = ReferralService.getInstance();

    public void createReferral(Referral referral) {
        if (referral == null) throw new IllegalArgumentException("referral must not be null");
        referralService.enqueueReferral(referral);
    }

    /**
     * Processes the next referral in the queue and appends to data/generated_referrals.txt
     * Returns generated text (useful to display in GUI).
     */
    public String processNextReferral(String suggestedRecipient) throws IOException {
        return referralService.processNextReferral(suggestedRecipient);
    }

    public int queueSize() {
        return referralService.queueSize();
    }
}

