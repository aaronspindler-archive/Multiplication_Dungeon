package theschoolproject;

public class Enemy extends Entity {

    int mode = 0; // 0 - random walk, 1 - player targeting
    int type = 0; // value ranges from 0-5, increasing in strength/ difficulty
    int pathDist = 0;
    int difficulty = 0;

    public Enemy(GamePanel gp, String sp) {
        super(gp, sp);
        this.isMoving = true;
    }

    @Override
    public void tick() {
        checkCollision();
        switch (mode) {
            case 0:
                if (pathDist <= 0) {
                    this.orientation = rand.nextInt(4);
                    pathDist = rand.nextInt(100);
                } else {
                    animCycle++;
                    pathDist--;
                    if (isMoving && spd < 1) {
                        spd = spd + 0.5;
                    }

                    if (!isMoving && spd > 0) {
                        spd = spd - 0.5;
                        animCycle = 1;
                    }

                    if (animCycle > 2) {
                        animCycle = 0;
                    }

                    switch (orientation) {
                        case 0:
                            if (!uBlock) {
                                setLocation(this.getX(), this.getY() - spd);
                            } else {
                                this.orientation = rand.nextInt(3);
                            }
                            break;
                        case 1:
                            if (!rBlock) {
                                setLocation(this.getX() + spd, this.getY());
                            } else {
                                this.orientation = rand.nextInt(3);
                            }
                            break;
                        case 2:
                            if (!dBlock) {
                                setLocation(this.getX(), this.getY() + spd);
                            } else {
                                this.orientation = rand.nextInt(3);
                            }
                            break;
                        case 3:
                            if (!lBlock) {
                                setLocation(this.getX() - spd, this.getY());
                            } else {
                                this.orientation = rand.nextInt(3);
                            }
                            break;
                    }
                }
                this.setLocation(this.xLoc + rand.nextDouble() - .5, this.yLoc + rand.nextDouble() - .5);

                break;
            case 1:
                //to be implemented
                break;

        }

    }

    public void randomWalk() {

    }

    public void targetPlayer() {

    }

}
