package spring.project.nyangmong.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import spring.project.nyangmong.web.dto.my.Outer;
import spring.project.nyangmong.web.dto.my.ResultList;

@Component
public class ContentSeqDownload {

    public List<Integer> contentSeqDown(Integer num) {

        RestTemplate tempRt = new RestTemplate();

        String tempUrl = "http://www.pettravel.kr/api/listPart.do?page=1&pageBlock=1&partCode=PC0" + num;
        Outer[] tempResponseDtos = tempRt.getForObject(tempUrl, Outer[].class);

        int count = tempResponseDtos[0].getTotalCount();

        List<Integer> numList = new ArrayList<>();

        RestTemplate rt = new RestTemplate();
        String url = "http://www.pettravel.kr/api/listPart.do?page=1&pageBlock=" + count + "&partCode=PC0" + num;
        Outer[] responseDtos = rt.getForObject(url, Outer[].class);

        List<ResultList> resultList = responseDtos[0].getResultList();

        System.out.println(resultList);

        for (int i = 0; i < resultList.size(); i++) {
            numList.add(resultList.get(i).getContentSeq());
        }

        // System.out.println(numList);

        return numList;
    }
}
