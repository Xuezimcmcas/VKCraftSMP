package com.neteasemc.vkcraftsmp.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvitationManager {
    private Map<UUID, Invitation> invitations;

    public InvitationManager() {
        this.invitations = new HashMap<>();
    }

    public void createInvitation(UUID inviter, UUID invitee) {
        Invitation invitation = new Invitation(inviter, invitee);
        invitations.put(invitee, invitation);
    }

    public Invitation getInvitation(UUID invitee) {
        return invitations.get(invitee);
    }
    public Player getInviter(UUID invitee) {
        Invitation invitation = invitations.get(invitee);
        if (invitation != null) {
            return Bukkit.getServer().getPlayer(invitation.getInviter());
        }
        return null;
    }

    public boolean isPlayerInvitedTo(UUID inviterUuid, UUID inviteeUuid) {
        Invitation invitation = invitations.get(inviteeUuid);
        return invitation != null && invitation.getInviter().equals(inviterUuid);
    }
    public void removeInvitation(UUID invitee) {
        invitations.remove(invitee);
    }
}

class Invitation {
    private UUID inviter;
    public UUID invitee;

    public Invitation(UUID inviter, UUID invitee) {
        this.inviter = inviter;
        this.invitee = invitee;
    }

    public UUID getInviter() {
        return this.inviter;
    }
}
