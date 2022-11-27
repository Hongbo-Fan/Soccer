package algonquin.cst2335.soccer;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SoccerMatchViewModel extends ViewModel {
    public MutableLiveData<ArrayList<SoccerMatchMessage>> matches = new MutableLiveData< >();
    public MutableLiveData<SoccerMatchMessage> selectedMatches = new MutableLiveData< >();
}
