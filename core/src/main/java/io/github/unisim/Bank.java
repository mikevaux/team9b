package io.github.unisim;

import java.text.NumberFormat;

/**
 * __NEW: WHOLE CLASS__ Represents a University's Bank Account, from where they can spend and receive money.
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

  /**
   * Debits the account balance by the given amount.
   *
   * @param amount the amount to debit the balance
   */
  public void debit(int amount) {
    balance -= amount;
  }

  /**
   * Credits the account balance by the given amount.
   *
   * @param amount the amount to credit the balance
   */
  public void credit(int amount) {
    balance += amount;
  }

  /**
   * Formats as currency and returns the account balance.
   *
   * @return the formatted string, e.g. £1,000,000
   */
  public String displayBalance() {
    NumberFormat f = NumberFormat.getInstance();
    f.setGroupingUsed(true);
    return GameState.getInstance().getCurrency() + f.format(balance);
  }

  public long getBalance() {
    return balance;
  }
}
