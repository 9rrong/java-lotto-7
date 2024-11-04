package lotto.view;

import java.util.List;

public class OutputView {

    public void printLottoCount(String lottoCount) {
        System.out.println(lottoCount + "개를 구매했습니다.");
    }

    public void printLottoNumbers(List<String> lottoNumbers) {
        lottoNumbers.forEach(System.out::println);
    }
}
