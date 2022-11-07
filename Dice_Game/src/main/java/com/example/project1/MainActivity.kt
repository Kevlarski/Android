package com.example.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import com.example.project1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPA.isInvisible=true
    }


    fun View.onRoll(){
        var die1 =(1..6).random()
        var die2 =(1..6).random()
        var score: Int
        var currentActive = binding.activePlayer.text
        var currentPlayerScore: Int

        if (binding.p1score.text.isNullOrBlank()){
            binding.p1score.text="0"
            binding.p2score.text="0"
        }
        //Rolling first die
        when(die1){
            1->binding.die1img.setImageResource(R.drawable.d1)
            2->binding.die1img.setImageResource(R.drawable.d2)
            3->binding.die1img.setImageResource(R.drawable.d3)
            4->binding.die1img.setImageResource(R.drawable.d4)
            5->binding.die1img.setImageResource(R.drawable.d5)
            6->binding.die1img.setImageResource(R.drawable.d6)
        }
        //Rolling second die
        when(die2){
            1->binding.die2img.setImageResource(R.drawable.d1)
            2->binding.die2img.setImageResource(R.drawable.d2)
            3->binding.die2img.setImageResource(R.drawable.d3)
            4->binding.die2img.setImageResource(R.drawable.d4)
            5->binding.die2img.setImageResource(R.drawable.d5)
            6->binding.die2img.setImageResource(R.drawable.d6)
        }

        //counter to increase trnTotal
        var trnTotal = binding.trnTotal.text.toString().toInt()
        trnTotal++
        binding.trnTotal.text = (trnTotal).toString()

        //if to see current player
        currentPlayerScore = if(binding.activePlayer.text=="P1")
                binding.p1score.text.toString().toInt()
            else
                binding.p2score.text.toString().toInt()


        //if for finding ones
        if(die1==1&&die2==1){
            currentPlayerScore=0
            playerSwitch()
        }//end if
        else if(die1==1||die2==1){
            playerSwitch()
        }//end elseif
        else{
            score = (die1+die2)+currentPlayerScore
            if(binding.activePlayer.text=="P1"){
                binding.p1score.text=score.toString()
            }//end if
            else{
                binding.p2score.text=score.toString()
            }//end else

            //if WinCon is met, make all text invisible,
            //except the win message and the Play Again button
            if(score>=50){
                binding.winrar.isInvisible=false
                binding.winrar.text = "$currentActive Wins!"
                binding.p1score.isInvisible=true
                binding.p2score.isInvisible=true
                binding.activePlayer.isInvisible=true
                binding.textView2.isInvisible=true
                binding.textView3.isInvisible=true
                binding.textView4.isInvisible=true
                binding.btnPA.isInvisible=false
            }
        }//end else

        //check for doubles to remove hold button
        binding.btnHold.isInvisible = die1==die2


    }//end onRoll

    fun View.onHold(){
        playerSwitch()
    }
    
    //Play Again button to reset
    fun View.onPA(){
		playerSwitch()
        binding.p1score.isInvisible=false
        binding.p1score.text = "0"
        binding.p2score.isInvisible = false
        binding.p2score.text = "0"
        binding.winrar.isInvisible=true
        binding.activePlayer.isInvisible=false
        binding.textView2.isInvisible=false
        binding.textView3.isInvisible=false
        binding.textView4.isInvisible=false
        binding.btnPA.isInvisible=true
        binding.trnTotal.text="0"
    }
    //Switches the active player
    private fun playerSwitch(){
        var currentActive = binding.activePlayer.text
        if(currentActive=="P1")
            binding.activePlayer.text = "P2"
        else
            binding.activePlayer.text = "P1"
    }//end playerSwitch

}//end class
