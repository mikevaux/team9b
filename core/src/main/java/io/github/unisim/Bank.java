package io.github.unisim;

import java.text.NumberFormat;

/**
 * Represents a University's Bank Account, from where they can spend and receive money.
 */
public class Bank {
  private static Bank INSTANCE;
  private long balance;

  /**
   * Creates a new Bank Account with the default initial balance.
   */
  private Bank() {
    balance = 1000000;
  }

  public static Bank getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Bank();
    }
    return INSTANCE;
  }

  public static void wipeInstance() {
    INSTANCE = null;
  }

  public void debit(int amount) {
    balance -= amount;
  }

  public void credit(int amount) {
    balance += amount;
  }

  public String displayBalance() {
    NumberFormat f = NumberFormat.getInstance();
    f.setGroupingUsed(true);
    return GameState.getInstance().getCurrency() + f.format(balance);
  }

  public long getBalance() {
    return balance;
  }
}
