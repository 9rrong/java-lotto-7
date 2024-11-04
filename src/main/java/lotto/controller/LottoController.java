package lotto.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lotto.model.Lotto;
import lotto.model.LottoBudget;
import lotto.model.LottoPrize;
import lotto.model.LottoPrizes;
import lotto.view.InputView;

public class LottoController {

    private final InputView inputView;

    public LottoController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        String lottoBudgetInput = inputView.readLottoBudget();
        LottoBudget lottoBudget = new LottoBudget(lottoBudgetInput);

        int lottoCount = lottoBudget.getLottoCount();
        System.out.println(lottoCount + "개를 구매했습니다.");

        List<Lotto> lottos = Stream.generate(() ->
                        {
                            List<Integer> randomNumbers = new ArrayList<>(Randoms.pickUniqueNumbersInRange(1, 45, 6));
                            randomNumbers.sort(Comparator.naturalOrder());
                            return new Lotto(randomNumbers);
                        }
                )
                .limit(lottoCount)
                .toList();

        lottos.stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);

        System.out.println("당첨 번호를 입력해 주세요.");
        String WinningNumbersInput = Console.readLine();
        String[] WinningNumberStrings = WinningNumbersInput.split(",");
        List<Integer> winningNumbers = Arrays.stream(WinningNumberStrings)
                .map(Integer::parseInt)
                .toList();

        System.out.println("보너스 번호를 입력해 주세요.");
        String BonusNumberInput = Console.readLine();
        int bonusNumber = Integer.parseInt(BonusNumberInput);

        LottoPrizes lottoPrizes = new LottoPrizes(lottos.stream().map(lotto -> {
            int matchCount = lotto.countMatchingNumbers(winningNumbers);
            boolean containsBonusNumber = lotto.containsNumber(bonusNumber);
            return LottoPrize.getLottoPrize(matchCount, containsBonusNumber);
        }).toList());

        String yield = lottoPrizes.calculateYield(lottoBudget.getValue());
        System.out.println("당첨 통계" + System.lineSeparator() + "---");

        List<String> matchStatistics = lottoPrizes.calculateMatchStatistics();
        matchStatistics.forEach(System.out::println);

        System.out.println("총 수익률은 " + yield + "%입니다.");
    }

}
