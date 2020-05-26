package jwzp2020.vet.model;

public enum TimeVisit {
    OneHour,
    HalfHour,
    OneQuart,
    ThreeQuart;

    public int getMinutes(){
        switch (this){
            case OneHour: return 60;
            case HalfHour: return 30;
            case OneQuart: return 15;
            case ThreeQuart: return 45;
        }
        return 0;
    }
}
