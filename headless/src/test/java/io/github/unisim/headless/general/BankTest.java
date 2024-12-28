package io.github.unisim.headless.general;

import io.github.unisim.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test 1.5.x.
 */
public class BankTest extends AbstractGeneralTest{
  private static Bank bank;
  private final long initialBalance = 1500000;
  private int change = 100;

  @BeforeEach
  public void boot(){
    Bank.wipeInstance();
    bank = Bank.getInstance();
  }

  /**
   * Test 1.5.1.
   */
  @Test
  public void testInitialBalance(){
    // check the initial balance is set correctly
    assertTrue(bank.getBalance() == initialBalance, "initial balance is not as expected");
  }

  /**
   * Test 1.5.2.
   */
  @Test
  public void testDebit(){
    // check balance is debited correctly
    long initial = bank.getBalance();
    bank.debit(change);
    assertTrue(bank.getBalance() == initial - change, "balance is not being debited as expected");
    // don't need to check for negative balance as this is handled in BuildingMenu
  }

  /**
   * Test 1.5.3.
   */
  @Test
  public void testCredit(){
    // check balance is credited correctly
    long initial = bank.getBalance();
    bank.credit(change);
    assertTrue(bank.getBalance() == initial + change, "balance is not being credited as expected");
  }

  /**
   * Test 1.5.4.
   */
  @Test
  public void testDisplayBalance(){
    // check the balance is displayed correctly
    long balance = bank.getBalance();
    assertEquals("£1,500,000", bank.displayBalance(), "balance is not displayed as expected");
    bank.debit((int)initialBalance);
    assertEquals("£0", bank.displayBalance(), "zero balance is not displayed as expected");
  }
}
