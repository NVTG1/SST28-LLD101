public class HardMode implements DiceRuleStrategy{
    public boolean canContinue(int consecutiveSixes){
        return consecutiveSixes < 3;
    }
}
